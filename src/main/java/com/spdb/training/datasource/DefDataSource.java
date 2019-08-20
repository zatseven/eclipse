package com.spdb.training.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.IdentityHashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import com.spdb.training.jdbc.core.DbUtils;

/**
 * 
 * @author wangxg3
 *
 */
public class DefDataSource extends AbsDataSource {

	// 补充定义默认的最大和最小空闲连接数
	protected int maxIdle = 1;
	protected int minIdle = 0;

	// 声明连接池和活跃连接池
	private volatile DefPooledConnection[] connections;
	private volatile IdentityHashMap<DefPooledConnection, Integer> activeConnetions;
	// 初始化连接池和活跃连接池计数器
	private volatile AtomicInteger presentCount = new AtomicInteger(0);
	private volatile AtomicInteger presentActiveCount = new AtomicInteger(0);
	// 连接池是否需要增长
	private volatile boolean needIncrement = false;

	public DefDataSource(String url, String user, String password, String driverClassName) {
		super(url, user, password, driverClassName);
	}

	public DefDataSource(Properties properties) {

		configFromProps(properties);

	}

	/**
	 * 基于properties 配置连接池属性
	 * 
	 * @param properties
	 */
	private void configFromProps(Properties properties) {
		{
			String property = properties.getProperty("ds.driverClassName");
			if (property != null) {
				setDriverClassName(property);
			}
		}

		{
			String property = properties.getProperty("ds.url");
			if (property != null) {
				setUrl(property);
			}
		}
		{
			String property = properties.getProperty("ds.user");
			if (property != null) {
				setUser(property);
			}
		}
		{
			String property = properties.getProperty("ds.password");
			if (property != null) {
				setPassword(property);
			}
		}
		{
			String property = properties.getProperty("ds.maxConn");
			if (property != null) {
				try {
					int value = Integer.parseInt(property);
					setMaxConn(value);
				} catch (NumberFormatException e) {
					logger.error("illegal property 'ds.maxConn'!", e);

				}
			}
		}
		{
			String property = properties.getProperty("ds.maxActive");
			if (property != null) {
				try {
					int value = Integer.parseInt(property);
					setMaxActive(value);
				} catch (NumberFormatException e) {
					logger.error("illegal property 'ds.maxActive'!", e);

				}
			}
		}

		{
			String property = properties.getProperty("ds.maxIdle");
			if (property != null) {
				try {
					int value = Integer.parseInt(property);
					setMaxIdle(value);
				} catch (NumberFormatException e) {
					logger.error("illegal property 'ds.maxIdle'!", e);

				}
			}
		}

		{
			String property = properties.getProperty("ds.minIdle");
			if (property != null) {
				try {
					int value = Integer.parseInt(property);
					setMinIdle(value);
				} catch (NumberFormatException e) {
					logger.error("illegal property 'ds.minIdle'!", e);

				}
			}
		}

		{
			String property = properties.getProperty("ds.increment");
			if (property != null) {
				try {
					int value = Integer.parseInt(property);
					setIncrement(value);
				} catch (NumberFormatException e) {
					logger.error("illegal property 'ds.increment'!", e);

				}
			}
		}

		{
			String property = properties.getProperty("ds.initCount");
			if (property != null) {
				try {
					int value = Integer.parseInt(property);
					setInitCount(value);
				} catch (NumberFormatException e) {
					logger.error("illegal property 'ds.initCount'!", e);

				}
			}
		}

	}

	public synchronized void init() throws SQLException {
		if (inited)
			return;
		logger.info("连接池初始化开始。。。");
		logger.info("检查连接池初始化参数合法性");
		if (!validInitParams()) {
			throw new SQLException("invalid init params!");
		}

		/*
		 * 注册jdbc驱动,如果为空，则代表使用自动注册模式
		 */
		if (driverClassName != null) {
			try {
				Class.forName(driverClassName);

			} catch (Exception e) {

				throw new SQLException("驱动注册失败！无法完成连接池初始化", e);
			}
		}

		// 初始化连接池数组
		connections = new DefPooledConnection[maxConn];
		activeConnetions = new IdentityHashMap<>(maxActive);
		// 填充连接池初始容量
		while (true) {

			if (presentCount.get() < initCount) {
				Connection conn;
				try {
					conn = generateNewConn(url, user, password);
				} catch (SQLException e) {
                    logger.error("创建连接失败！重试。。。",e);
					
					continue;
				}

				// 封装池连接
				ConnHolder connHolder = new ConnHolder(conn, presentCount.get(), this);
				@SuppressWarnings("resource")
				DefPooledConnection poolcConnection = new DefPooledConnection(connHolder);

				// 添加到连接池中
				connections[presentCount.get()] = poolcConnection;
				presentCount.addAndGet(1);
				logger.debug("当前池中连接数为：" + presentCount.get());

			} else {
				logger.info("连接池初始化完成，初始化连接数参数设定为" + initCount + ",当前池中实际共有连接数：" + presentCount.get());
				break;
			}
		}
       //初始化完成
		inited = true;

	}

	/**
	 * 校验连接池初始化参数合法性 maxIdle: minIdle, maxConn, initCount, maxActive,increment
	 * 
	 * 
	 */
	private boolean validInitParams() {

		logger.debug("连接池参数设置为：" + printPoolParamsConfig());

		// 保证池子可以至少提供一个连接
		if (maxConn <= 0 || maxIdle < 0 || minIdle < 0 || initCount < 0 || maxActive <= 0 || increment <= 0) {
			logger.error(
					"violated conf: maxConn <= 0 or maxIdle < 0 or minIdle < 0 or initCount < 0 or maxActive <= 0  !");
			return false;

		}
		if (increment > maxConn) {
			logger.error("invalid increment param!increment can not exceed maxConn!");
			return false;
		}
		
		if (maxIdle < minIdle || maxIdle > maxConn) {
			logger.error("violated conf:' maxIdle < minIdle or maxIdle > maxConn' ");
			return false;
		}

		if (initCount > maxConn) {
			logger.error("violated conf:'initCount > maxConn'  ");
			return false;

		}
		if (maxActive > maxConn) {
			logger.debug("condition:'maxActive > maxConn' not qualified!");
			return false;
		}

		return true;

	}

	@Override
	public synchronized DefPooledConnection getConnection() throws SQLException {
		// 保证连接池已经初始化
		init();
		// 首先判断活跃数是否已经满了
		DefPooledConnection conn = null;
		if (presentActiveCount.get() >= maxConn) {
			throw new SQLException("活跃连接数已经达到最大容量，暂时无可用连接！");
		}

		// 判断池中是否有空闲连接

		if (presentCount.get() > presentActiveCount.get()) {
			// 有空闲连接，去池子中取一个来
			conn = getInternalConn();
			// 继续判断连接池是否需要增加连接,判断在increment内部实现
			if (presentCount.get() < maxConn) {

				increment();
			}

			return conn;

		} else {
			// 池中无空闲连接，则执行increment操作，然后再取一次

			increment();

			return getInternalConn();

		}

	}

	/**
	 * 连接池模式不支持此种方式获取连接
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLException("unsupported method invoking!");

	}

	@Override
	protected synchronized void recycle(AbsPooledConnection connection) throws SQLException {
		// 保证初始化
		init();
		// 判断连接归属
		if (connection == null || !activeConnetions.containsKey(connection)) {
			throw new SQLException("此连接非当前连接池管理，不予回收！");
		}

		Connection physicalConn = connection.connHolder.conn;
		int connId = connection.connHolder.connId;

		// 判断连接是否活性的
		if (validConn(physicalConn)) {
			// 是活性的，重置连接后，重置状态inuse=false,直接放回连接池,从活跃资源池中踢出
			try {

				resetPhysicalConn(physicalConn);

			} catch (SQLException e) {
				// 即便reset出错也强制回收连接
				logger.error("reset conn failed!",e);
				connection.isReset = false;// 由于本次reset失败，获取连接时会再次reset

			}
			// 注册返仓
			activeConnetions.remove(connection);
			presentActiveCount.decrementAndGet();
			connection.inUse = false;
			connection.isRecycled = true;

		} else {
			/**
			 * 不是活性的，reborn之，再重新放回去
			 */

			// 装配一个新的池连接取代它
			@SuppressWarnings("resource")
			DefPooledConnection pooledConnection = null;
			try {
				pooledConnection = reborn(connection.connHolder.connId);

			} catch (SQLException e) {
				// 即使reborn出错也强制回收连接
				logger.error("reborn connection failed!",e);
				connection.isReset = false;// 但reset失败，获取连接时会再次检测是否reset

			}

			// 注册返仓
			activeConnetions.remove(connection);
			presentActiveCount.decrementAndGet();
			connection.inUse = false;
			connection.isRecycled = true;
			// 新连接放回到连接池中，还放在原来的位置
			connections[connId - 1] = pooledConnection;

		}
		logger.debug("连接回收完成！");

	}

	/**
	 * 从池中获取一个连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	private DefPooledConnection getInternalConn() throws SQLException {

		for (DefPooledConnection poolConn : connections) {
			// 查找可用连接
			if (poolConn != null && !poolConn.inUse) {
				int connId = poolConn.getConnHolder().getConnId();
				// 验证连接活性，若是活连接，则返回
				if (validConn(poolConn.getConnHolder().getConn())) {
					// 检验连接是否已reset
					if (!poolConn.isReset) {
						resetPhysicalConn(poolConn.getConnHolder().getConn());
					}

					// 注册出仓信息
					poolConn.inUse = true;
					activeConnetions.put(poolConn, connId);
					poolConn.isRecycled = false;// 出池子时置false
					presentActiveCount.addAndGet(1);
					// 返回连接
					return poolConn;
				} else {
					// 若是死连接，则reborn一个，放到池子中原来的位置，登记活跃性后，再返回
					DefPooledConnection newPooledConnection = reborn(connId);
					connections[connId] = newPooledConnection;
					// 注册出仓信息
					poolConn.inUse = true;
					activeConnetions.put(poolConn, connId);
					poolConn.isRecycled = false;// 出池子时置false
					presentActiveCount.addAndGet(1);
					// 返回连接
					return newPooledConnection;

				}

			}

		}
		// 都遍历结束也没有可用连接，则直接报错、

		throw new SQLException("连接池中无可用连接！");

	}

	/**
	 * 当池连接失效时，重新生成一个代替它的连接，使用相同的connId; reborn出的连接，一定可以保证是reset过的
	 * 
	 * @param connId
	 * @return
	 * @throws SQLException
	 */
	private DefPooledConnection reborn(int connId) throws SQLException {
		Connection conn = null;
		try {
			conn = generateNewConn(url, user, password);
		} catch (SQLException e) {
			
			DbUtils.closeConn(conn);
			throw e;
		}
		DefPooledConnection pooledConnection = new DefPooledConnection(new ConnHolder(conn, connId, this));
		return pooledConnection;

	}

	/**
	 * 增加连接池中的连接数
	 * 
	 * @throws SQLException
	 */
	private void increment() throws SQLException {
		// 前提条件：连接数已达最大值，不予增长
		if (presentCount.get() >= maxConn) {
			logger.warn("pool is full!Can not increment any more!");
			return;

		}
		// 当前实际空闲连接数
		int idleCount = presentCount.get() - presentActiveCount.get();
		logger.debug("将判断是否需要增长，当前连接池动态参数为" + printPoolDynamicParms());

		int actualIncrement = Math.min(Math.min(increment, maxIdle - idleCount),
				Math.max(0, maxConn - presentCount.get()));
		
		int countBeforeIncrement = presentCount.get();
		// 低于最低连接数，触发增长
		if (idleCount <= minIdle) {

			incrementByNum(countBeforeIncrement, actualIncrement);
			afterIncrement();
			// 判断下次是否需要继续增长，有两个判断条件 1）可用连接达到maxIdle 或 2）presentCount=maxConn
			// 达到则置needIncrement=false,没达到则置为true

			return;

		}
		// 在最大和最小之间，判断二级条件
		if (minIdle < idleCount && idleCount < maxIdle) {
			logger.debug("空闲连接数处于区间内");
			// 接着判断是否需要增长
			if (needIncrement) {
				// 增长
				logger.debug("needIncrement=true，需要增长");
				incrementByNum(countBeforeIncrement, actualIncrement);
				afterIncrement();

			} else {
				logger.debug("needIncrement=false,无需增长");

			}

			return;
		}
		// 其他情况则不需要增长了
		logger.debug("空余连接数充足无需增长");
	}

	/**
	 * 增长过后，需要修改needIncrement的变量 暂时放在increment()中调用
	 */
	private void afterIncrement() {
		if (presentCount.get() - presentActiveCount.get() >= maxIdle || presentCount.get() >= maxConn) {
			needIncrement = false;
		} else {
			needIncrement = true;
		}

	}

	/**
	 * 往连接池中放入指定数量的连接
	 * 
	 * @throws SQLException
	 */
	private void incrementByNum(int countBeforeIncrement, int actualIncrement) throws SQLException {
		logger.debug("当前池中连接数为{},本次预计新增{}个新连接", countBeforeIncrement, actualIncrement);
		if (actualIncrement == 0) {
			logger.debug("实际期望增长数为0，将不执行增长操作");
			return;

		}
		for (int i = 0; i < actualIncrement; i++) {
			// 生成物理连接
			Connection physicalConn = generateNewConn(url, user, password);
			// 封装池连接
			@SuppressWarnings("resource")
			DefPooledConnection pooledConnection = new DefPooledConnection(
					new ConnHolder(physicalConn, countBeforeIncrement + i, this));
			logger.debug("新增连接的connId=" + (countBeforeIncrement + i));
			// 装入连接池
			connections[countBeforeIncrement + i] = pooledConnection;
			presentCount.addAndGet(1);
			logger.debug("增长中，当前池中连接数为：{}", presentCount.get());

		}
		logger.debug("本次增长结束，当前池中连接数为：", presentCount.get());

	}

	/**
	 * 生成一个物理连接，并按照默认参数格式化
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	private Connection generateNewConn(String url, String user, String password) throws SQLException {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
			// 验证连接，若通过，则封装池连接
			if (validConn(conn)) {
				resetPhysicalConn(conn);

			} else {
				DbUtils.closeConn(conn);

				throw new SQLException("无效连接！");
			}
		} catch (SQLException e) {
			
			DbUtils.closeConn(conn);
			throw new SQLException("创建物理连接失败！",e);
		} finally {

		}

		return conn;

	}

	/**
	 * 重置回收后的连接
	 * 
	 * @param connection
	 * @throws SQLException
	 */
	private void resetPhysicalConn(Connection conn) throws SQLException {
		// 给连接赋予默认连接属性

		try {
			conn.setAutoCommit(defaultAutoCommit);
			conn.setReadOnly(defaultReadOnly);
			if (defaultTransactionIsolation == null) {
				defaultTransactionIsolation = conn.getTransactionIsolation();
			} else {
				conn.setTransactionIsolation(defaultTransactionIsolation);
			}
		} catch (SQLException e) {
			
			DbUtils.closeConn(conn);
			throw e;
		}

	}

	/**
	 * 验证连接的活性
	 * 
	 * @param conn
	 * @return
	 */
	private boolean validConn(Connection conn) {

		try {
			if (conn != null && !conn.isClosed() && conn.isValid(0)) {
				return true;
			}
		} catch (SQLException e) {
			DbUtils.closeConn(conn);
			
			logger.warn("无效连接");
		}
		return false;

	}

	/**
	 * 打印连接池参数设置
	 * 
	 * @return
	 */
	public String printPoolParamsConfig() {
		return "DefDataSource [maxIdle=" + maxIdle + ", minIdle=" + minIdle + ", url=" + url + ", user=" + user
				+ ", driverClassName=" + driverClassName + ", maxConn=" + maxConn + ", maxActive=" + maxActive
				+ ", initCount=" + initCount + ", increment=" + increment + "]";
	}

	/**
	 * 连接池连接的缺省配置
	 * 
	 * @return
	 */
	public String printDefConnConf() {
		return "DefDataSource's default physical connections settings: [defaultAutoCommit=" + defaultAutoCommit
				+ ", defaultReadOnly=" + defaultReadOnly + ", defaultTransactionIsolation="
				+ defaultTransactionIsolation + ", defaultCatalog=" + defaultCatalog + "]";
	}

	/**
	 * 打印连接池当前的动态参数
	 * 
	 * @return
	 */
	public String printPoolDynamicParms() {
		return "DefDataSource [presentCount=" + presentCount.get() + ", presentActiveCount=" + presentActiveCount.get()
				+ ", needIncrement=" + needIncrement + "]";
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		if (!inited)
			this.maxIdle = maxIdle;
	}

	public int getMinIdle() {

		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		if (!inited)
			this.minIdle = minIdle;
	}

}

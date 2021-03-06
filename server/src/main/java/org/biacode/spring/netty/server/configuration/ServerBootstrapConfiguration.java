package org.biacode.spring.netty.server.configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Arthur Asatryan.
 * Date: 1/31/18
 * Time: 4:02 PM
 */
@Configuration
public class ServerBootstrapConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrapConfiguration.class);
    //region Dependencies
    @Autowired
    private NioEventLoopGroup bossGroup;

    @Autowired
    private NioEventLoopGroup workerGroup;

    @Autowired
    private ChannelInitializer<SocketChannel> channelInitializer;
    //endregion

    //region Public methods
    @Bean(name = "serverBootstrap")
    public ServerBootstrap serverBootstrap() {
        LOGGER.debug("Bootstrapping websocket server...");
        return new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(channelInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }
    //endregion

}

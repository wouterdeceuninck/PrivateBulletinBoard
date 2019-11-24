package presentation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import presentation.port.BulletinBoardInterface;

@Configuration
public class RemoteProxyConfig {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/BulletinBoardService");
        rmiProxyFactory.setServiceInterface(BulletinBoardInterface.class);
        return rmiProxyFactory;
    }


}

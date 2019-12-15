package presentation.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import shared.BulletinBoardInterface;

@Configuration
public class RemoteProxyConfig {

    private String rmiLocationUrl = "rmi://localhost:1099/BulletinBoardService";

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    BulletinBoardInterface service() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl(rmiLocationUrl);
        rmiProxyFactoryBean.setServiceInterface(BulletinBoardInterface.class);
        rmiProxyFactoryBean.setCacheStub(true);
        rmiProxyFactoryBean.setLookupStubOnStartup(true);
        rmiProxyFactoryBean.setRefreshStubOnConnectFailure(true);
        rmiProxyFactoryBean.afterPropertiesSet();
        return (BulletinBoardInterface) rmiProxyFactoryBean.getObject();
    }
}

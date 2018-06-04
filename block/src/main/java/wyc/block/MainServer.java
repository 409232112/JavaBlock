package wyc.block;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import wyc.block.exception.BlockException;
import wyc.block.util.ServerUtil;

@Component
public class MainServer implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments var1) throws BlockException {
        ServerUtil.startServer("17ctzAvQZYKEZyNVhLh9wteyabiC3ePWaG");
    }
}

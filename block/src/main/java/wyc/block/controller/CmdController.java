package wyc.block.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import wyc.block.exception.BlockException;
import wyc.block.util.CommonUtility;
import wyc.block.util.blockchain.BlockChainUtil;
import wyc.block.util.blockchain.WalletUtil;
import wyc.block.util.transacation.TransactionUtil;
import wyc.block.util.transacation.UTXOUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "cmd", produces = "application/json;charset=UTF-8")
public class CmdController {

    private static final Logger logger = Logger.getLogger(CmdController.class);

    /**
     * 发送币
     * @param param
     * @return
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public String send(@RequestBody Map<String, Object> param) throws BlockException{
        if(param.isEmpty()){
            return CommonUtility.constructResultJson("-1","缺少参数！");
        }else if(!param.containsKey("from")||!param.containsKey("to")||!param.containsKey("amount")||!param.containsKey("mineNow")){
            return CommonUtility.constructResultJson("-1","参数错误！");
        }else if(param.get("from").toString().length()==0||param.get("to").toString().length()==0||param.get("amount").toString().length()==0||param.get("mineNow").toString().length()==0){
            return CommonUtility.constructResultJson("-1","参数错误！");
        }
        String from = param.get("from").toString();
        String to = param.get("to").toString();
        int amount =Integer.valueOf(param.get("amount").toString());
        boolean mineNow = Integer.valueOf(param.get("mineNow").toString())==0?false:true;
        TransactionUtil.send(from,to,amount,mineNow);
        logger.info("send success");
        return CommonUtility.constructResultJson("0","发送成功！");
    }

    /**
     * 创建区块链
     * @throws BlockException
     */
    @RequestMapping(value = "createBlockChain", method = RequestMethod.GET)
    public String createBlockChain() throws BlockException{
        String address = WalletUtil.createWallet();
        BlockChainUtil.createBlockChain(address);
        Map data = new HashMap();
        data.put("address",address);
        return CommonUtility.constructResultJson("0","创建区块链成功！",data);
    }

    /**
     * 打印区块链
     */
    @RequestMapping(value = "printChain", method = RequestMethod.GET)
    public String printChain() throws BlockException{
        BlockChainUtil.printChain();
        return CommonUtility.constructResultJson("0","打印成功！");
    }

    /**
     * 生成新钱包
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "createWallet", method = RequestMethod.GET)
    public String createWallet() throws BlockException{
        String address = WalletUtil.createWallet();
        logger.info("create wallet success :"+ address);
        Map data = new HashMap();
        data.put("address",address);
        return CommonUtility.constructResultJson("0","创建钱包成功！",data);
    }

    /**
     * 获取钱包余额
     * @param address
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getBalance/{address}", method = RequestMethod.GET)
    public String getBalance(@PathVariable String address) throws BlockException{
        int balance = TransactionUtil.getBalance(address);
        logger.info("Balance of "+address+" : "+balance);
        Map data = new HashMap();
        data.put("balance",balance);
        return CommonUtility.constructResultJson("0","查询成功！",data);

    }

    /**
     * 打印钱包
     * @throws Exception
     */
    @RequestMapping(value = "printWallet", method = RequestMethod.GET)
    public String printWallet() throws BlockException{
        WalletUtil.printWallet();
        return CommonUtility.constructResultJson("0","打印成功！");
    }



    @RequestMapping(value = "reindexUTX0", method = RequestMethod.GET)
    public String reindexUTX0() throws BlockException {
        UTXOUtil.reIndex();
        return CommonUtility.constructResultJson("0","reindex UTX0 success !");
    }
}

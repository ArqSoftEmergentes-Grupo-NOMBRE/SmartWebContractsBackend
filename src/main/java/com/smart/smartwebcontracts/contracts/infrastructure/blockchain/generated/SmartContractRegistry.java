package com.smart.smartwebcontracts.contracts.infrastructure.blockchain.generated;



import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class SmartContractRegistry extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b50610f318061001c5f395ff3fe608060405234801561000f575f5ffd5b506004361061004a575f3560e01c8063160121d91461004e578063474da79a1461006a578063534860cc1461009f5780636ebc8c86146100bd575b5f5ffd5b610068600480360381019061006391906109b8565b6100f2565b005b610084600480360381019061007f9190610acd565b610207565b60405161009696959493929190610b67565b60405180910390f35b6100a76104eb565b6040516100b49190610be9565b60405180910390f35b6100d760048036038101906100d29190610acd565b6104f6565b6040516100e996959493929190610b67565b60405180910390f35b5f6040518060c0016040528088815260200187815260200186815260200185815260200184815260200183815250908060018154018082558091505060019003905f5260205f2090600602015f909190919091505f820151815f0190816101599190610dff565b50602082015181600101908161016f9190610dff565b5060408201518160020190816101859190610dff565b50606082015181600301908161019b9190610dff565b506080820151816004015560a08201518160050190816101bb9190610dff565b5050507fcff6fe9806a5dd3ae1a1be0367ed092b14cd04fd84cc111e95171a7d84cba9d68686868686866040516101f796959493929190610b67565b60405180910390a1505050505050565b5f8181548110610215575f80fd5b905f5260205f2090600602015f91509050805f01805461023490610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461026090610c2f565b80156102ab5780601f10610282576101008083540402835291602001916102ab565b820191905f5260205f20905b81548152906001019060200180831161028e57829003601f168201915b5050505050908060010180546102c090610c2f565b80601f01602080910402602001604051908101604052809291908181526020018280546102ec90610c2f565b80156103375780601f1061030e57610100808354040283529160200191610337565b820191905f5260205f20905b81548152906001019060200180831161031a57829003601f168201915b50505050509080600201805461034c90610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461037890610c2f565b80156103c35780601f1061039a576101008083540402835291602001916103c3565b820191905f5260205f20905b8154815290600101906020018083116103a657829003601f168201915b5050505050908060030180546103d890610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461040490610c2f565b801561044f5780601f106104265761010080835404028352916020019161044f565b820191905f5260205f20905b81548152906001019060200180831161043257829003601f168201915b50505050509080600401549080600501805461046a90610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461049690610c2f565b80156104e15780601f106104b8576101008083540402835291602001916104e1565b820191905f5260205f20905b8154815290600101906020018083116104c457829003601f168201915b5050505050905086565b5f5f80549050905090565b6060806060805f60605f5f888154811061051357610512610ece565b5b905f5260205f2090600602016040518060c00160405290815f8201805461053990610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461056590610c2f565b80156105b05780601f10610587576101008083540402835291602001916105b0565b820191905f5260205f20905b81548152906001019060200180831161059357829003601f168201915b505050505081526020016001820180546105c990610c2f565b80601f01602080910402602001604051908101604052809291908181526020018280546105f590610c2f565b80156106405780601f1061061757610100808354040283529160200191610640565b820191905f5260205f20905b81548152906001019060200180831161062357829003601f168201915b5050505050815260200160028201805461065990610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461068590610c2f565b80156106d05780601f106106a7576101008083540402835291602001916106d0565b820191905f5260205f20905b8154815290600101906020018083116106b357829003601f168201915b505050505081526020016003820180546106e990610c2f565b80601f016020809104026020016040519081016040528092919081815260200182805461071590610c2f565b80156107605780601f1061073757610100808354040283529160200191610760565b820191905f5260205f20905b81548152906001019060200180831161074357829003601f168201915b505050505081526020016004820154815260200160058201805461078390610c2f565b80601f01602080910402602001604051908101604052809291908181526020018280546107af90610c2f565b80156107fa5780601f106107d1576101008083540402835291602001916107fa565b820191905f5260205f20905b8154815290600101906020018083116107dd57829003601f168201915b5050505050815250509050805f015181602001518260400151836060015184608001518560a001519650965096509650965096505091939550919395565b5f604051905090565b5f5ffd5b5f5ffd5b5f5ffd5b5f5ffd5b5f601f19601f8301169050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b61089782610851565b810181811067ffffffffffffffff821117156108b6576108b5610861565b5b80604052505050565b5f6108c8610838565b90506108d4828261088e565b919050565b5f67ffffffffffffffff8211156108f3576108f2610861565b5b6108fc82610851565b9050602081019050919050565b828183375f83830152505050565b5f610929610924846108d9565b6108bf565b9050828152602081018484840111156109455761094461084d565b5b610950848285610909565b509392505050565b5f82601f83011261096c5761096b610849565b5b813561097c848260208601610917565b91505092915050565b5f819050919050565b61099781610985565b81146109a1575f5ffd5b50565b5f813590506109b28161098e565b92915050565b5f5f5f5f5f5f60c087890312156109d2576109d1610841565b5b5f87013567ffffffffffffffff8111156109ef576109ee610845565b5b6109fb89828a01610958565b965050602087013567ffffffffffffffff811115610a1c57610a1b610845565b5b610a2889828a01610958565b955050604087013567ffffffffffffffff811115610a4957610a48610845565b5b610a5589828a01610958565b945050606087013567ffffffffffffffff811115610a7657610a75610845565b5b610a8289828a01610958565b9350506080610a9389828a016109a4565b92505060a087013567ffffffffffffffff811115610ab457610ab3610845565b5b610ac089828a01610958565b9150509295509295509295565b5f60208284031215610ae257610ae1610841565b5b5f610aef848285016109a4565b91505092915050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f610b2a82610af8565b610b348185610b02565b9350610b44818560208601610b12565b610b4d81610851565b840191505092915050565b610b6181610985565b82525050565b5f60c0820190508181035f830152610b7f8189610b20565b90508181036020830152610b938188610b20565b90508181036040830152610ba78187610b20565b90508181036060830152610bbb8186610b20565b9050610bca6080830185610b58565b81810360a0830152610bdc8184610b20565b9050979650505050505050565b5f602082019050610bfc5f830184610b58565b92915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f6002820490506001821680610c4657607f821691505b602082108103610c5957610c58610c02565b5b50919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f60088302610cbb7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610c80565b610cc58683610c80565b95508019841693508086168417925050509392505050565b5f819050919050565b5f610d00610cfb610cf684610985565b610cdd565b610985565b9050919050565b5f819050919050565b610d1983610ce6565b610d2d610d2582610d07565b848454610c8c565b825550505050565b5f5f905090565b610d44610d35565b610d4f818484610d10565b505050565b5b81811015610d7257610d675f82610d3c565b600181019050610d55565b5050565b601f821115610db757610d8881610c5f565b610d9184610c71565b81016020851015610da0578190505b610db4610dac85610c71565b830182610d54565b50505b505050565b5f82821c905092915050565b5f610dd75f1984600802610dbc565b1980831691505092915050565b5f610def8383610dc8565b9150826002028217905092915050565b610e0882610af8565b67ffffffffffffffff811115610e2157610e20610861565b5b610e2b8254610c2f565b610e36828285610d76565b5f60209050601f831160018114610e67575f8415610e55578287015190505b610e5f8582610de4565b865550610ec6565b601f198416610e7586610c5f565b5f5b82811015610e9c57848901518255600182019150602085019450602081019050610e77565b86831015610eb95784890151610eb5601f891682610dc8565b8355505b6001600288020188555050505b505050505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffdfea2646970667358221220e0d34e415f359986cdc4d47c7978e811b247caff2ae9acb607900a1d305af55c64736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CONTRACTS = "contracts";

    public static final String FUNC_CREATECONTRACT = "createContract";

    public static final String FUNC_GETCONTRACT = "getContract";

    public static final String FUNC_GETTOTALCONTRACTS = "getTotalContracts";

    public static final Event CONTRACTCREATED_EVENT = new Event("ContractCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected SmartContractRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmartContractRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmartContractRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmartContractRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ContractCreatedEventResponse> getContractCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONTRACTCREATED_EVENT, transactionReceipt);
        ArrayList<ContractCreatedEventResponse> responses = new ArrayList<ContractCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContractCreatedEventResponse typedResponse = new ContractCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.contractId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.clientId = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.developerId = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.webServiceId = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.startDate = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.status = (String) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ContractCreatedEventResponse getContractCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONTRACTCREATED_EVENT, log);
        ContractCreatedEventResponse typedResponse = new ContractCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.contractId = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.clientId = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.developerId = (String) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.webServiceId = (String) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.startDate = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        typedResponse.status = (String) eventValues.getNonIndexedValues().get(5).getValue();
        return typedResponse;
    }

    public Flowable<ContractCreatedEventResponse> contractCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getContractCreatedEventFromLog(log));
    }

    public Flowable<ContractCreatedEventResponse> contractCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTRACTCREATED_EVENT));
        return contractCreatedEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple6<String, String, String, String, BigInteger, String>> contracts(
            BigInteger param0) {
        final Function function = new Function(FUNC_CONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, String, BigInteger, String>>(function,
                new Callable<Tuple6<String, String, String, String, BigInteger, String>>() {
                    @Override
                    public Tuple6<String, String, String, String, BigInteger, String> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, String, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> createContract(String _contractId,
            String _clientId, String _developerId, String _webServiceId, BigInteger _startDate,
            String _status) {
        final Function function = new Function(
                FUNC_CREATECONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_contractId), 
                new org.web3j.abi.datatypes.Utf8String(_clientId), 
                new org.web3j.abi.datatypes.Utf8String(_developerId), 
                new org.web3j.abi.datatypes.Utf8String(_webServiceId), 
                new org.web3j.abi.datatypes.generated.Uint256(_startDate), 
                new org.web3j.abi.datatypes.Utf8String(_status)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple6<String, String, String, String, BigInteger, String>> getContract(
            BigInteger index) {
        final Function function = new Function(FUNC_GETCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple6<String, String, String, String, BigInteger, String>>(function,
                new Callable<Tuple6<String, String, String, String, BigInteger, String>>() {
                    @Override
                    public Tuple6<String, String, String, String, BigInteger, String> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, String, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getTotalContracts() {
        final Function function = new Function(FUNC_GETTOTALCONTRACTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static SmartContractRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartContractRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmartContractRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmartContractRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmartContractRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartContractRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartContractRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartContractRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartContractRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SmartContractRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<SmartContractRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SmartContractRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<SmartContractRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SmartContractRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<SmartContractRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SmartContractRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }



    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ContractCreatedEventResponse extends BaseEventResponse {
        public String contractId;

        public String clientId;

        public String developerId;

        public String webServiceId;

        public BigInteger startDate;

        public String status;
    }
}

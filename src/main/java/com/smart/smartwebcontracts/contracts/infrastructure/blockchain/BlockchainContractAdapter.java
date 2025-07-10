package com.smart.smartwebcontracts.contracts.infrastructure.blockchain;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.HashRecordDTO;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.SmartContractDTO;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.generated.HashRegistry;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.generated.SmartContractRegistry;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BlockchainContractAdapter {

    private final Web3j web3j;
    private final Credentials credentials;
    private final String contractAddress;
    private final String smartContractRegistryAddress;


    public BlockchainContractAdapter(
            Web3j web3j,
            Credentials credentials,
            @Value("${contract.hashregistry.address}") String contractAddress,
            @Value("${contract.smartcontractregistry.address}") String smartContractRegistryAddress

    ) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.contractAddress = contractAddress;
        this.smartContractRegistryAddress = smartContractRegistryAddress;

    }

    public String registerContractHashOnBlockchain(String hash) {
        try {
            log.info("Loading smart contract at address {}", contractAddress);

            HashRegistry contract = HashRegistry.load(
                    contractAddress,
                    web3j,
                    credentials,
                    new DefaultGasProvider()
            );

            log.info("Calling registerHash(...) in smart contract...");
            TransactionReceipt txReceipt = contract.registerHash(hash).send();
            return txReceipt.getTransactionHash();

        } catch (Exception e) {
            log.error("Failed to register contract hash on blockchain", e);
            throw new RuntimeException("Blockchain operation failed", e);
        }
    }


    public String registerFullContractOnBlockchain(Contract contract) {
        try {
            log.info("Loading SmartContractRegistry at address {}", smartContractRegistryAddress);

            SmartContractRegistry smartContract = SmartContractRegistry.load(
                    smartContractRegistryAddress,
                    web3j,
                    credentials,
                    new DefaultGasProvider()
            );

            String contractId = contract.getId().toString();
            String clientId = contract.getClientId().toString();
            String developerId = contract.getDeveloperId().toString();
            String webServiceId = contract.getWebServiceId().toString();
            BigInteger startDate = BigInteger.valueOf(contract.getFechaInicio().toEpochDay());
            String status = contract.getStatus();

            log.info("Calling createContract(...) with data: {}, {}, {}, {}, {}, {}",
                    contractId, clientId, developerId, webServiceId, startDate, status);

            TransactionReceipt txReceipt = smartContract.createContract(
                    contractId, clientId, developerId, webServiceId, startDate, status
            ).send();

            log.info("Contract stored in blockchain with txHash {}", txReceipt.getTransactionHash());
            return txReceipt.getTransactionHash();

        } catch (Exception e) {
            log.error("Error registering full contract in blockchain", e);
            throw new RuntimeException("Blockchain operation failed", e);
        }
    }

    public List<HashRecordDTO> getAllStoredHashes() {
        try {
            log.info("Loading smart contract at address {}", contractAddress);

            HashRegistry contract = HashRegistry.load(
                    contractAddress,
                    web3j,
                    credentials,
                    new DefaultGasProvider()
            );

            log.info("Fetching total number of stored hashes...");
            BigInteger totalHashes = contract.getTotalHashes().send();
            log.info("Total hashes: {}", totalHashes);

            List<HashRecordDTO> hashes = new ArrayList<>();

            for (BigInteger i = BigInteger.ZERO; i.compareTo(totalHashes) < 0; i = i.add(BigInteger.ONE)) {
                var tuple = contract.getHash(i).send(); // Tuple3<String, BigInteger, String>
                hashes.add(new HashRecordDTO(
                        tuple.component1(), // hash
                        tuple.component2(), // timestamp
                        tuple.component3()  // sender
                ));
            }

            return hashes;

        } catch (Exception e) {
            log.error("Failed to fetch hashes from blockchain", e);
            throw new RuntimeException("Blockchain query failed", e);
        }
    }

    public List<SmartContractDTO> getAllSmartContracts() {
        try {
            log.info("Loading SmartContractRegistry at address {}", smartContractRegistryAddress);

            SmartContractRegistry smartContract = SmartContractRegistry.load(
                    smartContractRegistryAddress,
                    web3j,
                    credentials,
                    new DefaultGasProvider()
            );

            BigInteger totalContracts = smartContract.getTotalContracts().send();
            log.info("Total smart contracts: {}", totalContracts);

            List<SmartContractDTO> contracts = new ArrayList<>();

            for (BigInteger i = BigInteger.ZERO; i.compareTo(totalContracts) < 0; i = i.add(BigInteger.ONE)) {
                var tuple = smartContract.getContract(i).send(); // Tuple6
                contracts.add(new SmartContractDTO(
                        tuple.component1(),
                        tuple.component2(),
                        tuple.component3(),
                        tuple.component4(),
                        tuple.component5(),
                        tuple.component6()
                ));
            }

            return contracts;

        } catch (Exception e) {
            log.error("Failed to fetch smart contracts from blockchain", e);
            throw new RuntimeException("Blockchain query failed", e);
        }
    }




}

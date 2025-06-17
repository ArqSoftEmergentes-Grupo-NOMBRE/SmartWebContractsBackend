// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

contract HashRegistry {

    struct HashRecord {
        string hash;
        uint256 timestamp;
        address sender;
    }

    HashRecord[] public records;

    event HashRegistered(string hash, address indexed sender, uint256 timestamp);

    function registerHash(string memory _hash) public {
        records.push(HashRecord({
            hash: _hash,
            timestamp: block.timestamp,
            sender: msg.sender
        }));

        emit HashRegistered(_hash, msg.sender, block.timestamp);
    }

    function getHash(uint256 index) public view returns (string memory, uint256, address) {
        require(index < records.length, "Index out of bounds");
        HashRecord memory r = records[index];
        return (r.hash, r.timestamp, r.sender);
    }

    function getTotalHashes() public view returns (uint256) {
        return records.length;
    }
}

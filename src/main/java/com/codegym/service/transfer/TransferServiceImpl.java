package com.codegym.service.transfer;

import com.codegym.model.Transfer;
import com.codegym.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class TransferServiceImpl implements ITransferService{
    @Autowired
    private TransferRepository transferRepository;
    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Transfer getById(Long id) {
        return null;
    }

    @Override
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void delete(Transfer transfer) {

    }


    @Override
    public void deleteById(Long id) {

    }
}

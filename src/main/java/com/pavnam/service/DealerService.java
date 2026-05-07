package com.pavnam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pavnam.model.Dealers;
import com.pavnam.repository.DealerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    
    public Dealers getDealerById(Long id) {
        return dealerRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Dealer not found with id: " + id));
    }

    public Dealers getDealerByEmail(String email) {
        return dealerRepository.findByEmail(email);
    }
    
    public void saveDealer(Dealers dealer) {
        dealerRepository.save(dealer);
    }

    public Dealers updateDealer(Long id, Dealers updatedDealer) {
        Dealers existingDealer = getDealerById(id);
        
        // Update fields
        existingDealer.setFullName(updatedDealer.getFullName());
        existingDealer.setMobile(updatedDealer.getMobile());
        existingDealer.setCity(updatedDealer.getCity());
        existingDealer.setState(updatedDealer.getState());
        existingDealer.setAddress(updatedDealer.getAddress());
        existingDealer.setGovtId(updatedDealer.getGovtId());
        existingDealer.setGovtIdNumber(updatedDealer.getGovtIdNumber());
        
        // Don't update file paths here as they're handled separately
        return dealerRepository.save(existingDealer);
    }

    public void deleteDealer(Long id) {
        dealerRepository.deleteById(id);
    }

    public Dealers getDealerByUsername(String username) {
        return dealerRepository.findByUsername(username).orElse(null);
    }
    
    
    
    
}
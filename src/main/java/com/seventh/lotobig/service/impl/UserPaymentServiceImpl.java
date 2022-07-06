package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.UserPayment;
import com.seventh.lotobig.repository.UserPaymentRepository;
import com.seventh.lotobig.service.UserPaymentService;
import com.seventh.lotobig.service.dto.UserPaymentDTO;
import com.seventh.lotobig.service.mapper.UserPaymentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserPayment}.
 */
@Service
@Transactional
public class UserPaymentServiceImpl implements UserPaymentService {

    private final Logger log = LoggerFactory.getLogger(UserPaymentServiceImpl.class);

    private final UserPaymentRepository userPaymentRepository;

    private final UserPaymentMapper userPaymentMapper;

    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository, UserPaymentMapper userPaymentMapper) {
        this.userPaymentRepository = userPaymentRepository;
        this.userPaymentMapper = userPaymentMapper;
    }

    @Override
    public UserPaymentDTO save(UserPaymentDTO userPaymentDTO) {
        log.debug("Request to save UserPayment : {}", userPaymentDTO);
        UserPayment userPayment = userPaymentMapper.toEntity(userPaymentDTO);
        userPayment = userPaymentRepository.save(userPayment);
        return userPaymentMapper.toDto(userPayment);
    }

    @Override
    public UserPaymentDTO update(UserPaymentDTO userPaymentDTO) {
        log.debug("Request to save UserPayment : {}", userPaymentDTO);
        UserPayment userPayment = userPaymentMapper.toEntity(userPaymentDTO);
        userPayment = userPaymentRepository.save(userPayment);
        return userPaymentMapper.toDto(userPayment);
    }

    @Override
    public Optional<UserPaymentDTO> partialUpdate(UserPaymentDTO userPaymentDTO) {
        log.debug("Request to partially update UserPayment : {}", userPaymentDTO);

        return userPaymentRepository
            .findById(userPaymentDTO.getId())
            .map(existingUserPayment -> {
                userPaymentMapper.partialUpdate(existingUserPayment, userPaymentDTO);

                return existingUserPayment;
            })
            .map(userPaymentRepository::save)
            .map(userPaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserPaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPayments");
        return userPaymentRepository.findAll(pageable).map(userPaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserPaymentDTO> findOne(Long id) {
        log.debug("Request to get UserPayment : {}", id);
        return userPaymentRepository.findById(id).map(userPaymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserPayment : {}", id);
        userPaymentRepository.deleteById(id);
    }
}

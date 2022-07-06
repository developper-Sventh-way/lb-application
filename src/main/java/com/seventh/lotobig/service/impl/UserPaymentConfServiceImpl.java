package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.UserPaymentConf;
import com.seventh.lotobig.repository.UserPaymentConfRepository;
import com.seventh.lotobig.service.UserPaymentConfService;
import com.seventh.lotobig.service.dto.UserPaymentConfDTO;
import com.seventh.lotobig.service.mapper.UserPaymentConfMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserPaymentConf}.
 */
@Service
@Transactional
public class UserPaymentConfServiceImpl implements UserPaymentConfService {

    private final Logger log = LoggerFactory.getLogger(UserPaymentConfServiceImpl.class);

    private final UserPaymentConfRepository userPaymentConfRepository;

    private final UserPaymentConfMapper userPaymentConfMapper;

    public UserPaymentConfServiceImpl(UserPaymentConfRepository userPaymentConfRepository, UserPaymentConfMapper userPaymentConfMapper) {
        this.userPaymentConfRepository = userPaymentConfRepository;
        this.userPaymentConfMapper = userPaymentConfMapper;
    }

    @Override
    public UserPaymentConfDTO save(UserPaymentConfDTO userPaymentConfDTO) {
        log.debug("Request to save UserPaymentConf : {}", userPaymentConfDTO);
        UserPaymentConf userPaymentConf = userPaymentConfMapper.toEntity(userPaymentConfDTO);
        userPaymentConf = userPaymentConfRepository.save(userPaymentConf);
        return userPaymentConfMapper.toDto(userPaymentConf);
    }

    @Override
    public UserPaymentConfDTO update(UserPaymentConfDTO userPaymentConfDTO) {
        log.debug("Request to save UserPaymentConf : {}", userPaymentConfDTO);
        UserPaymentConf userPaymentConf = userPaymentConfMapper.toEntity(userPaymentConfDTO);
        userPaymentConf = userPaymentConfRepository.save(userPaymentConf);
        return userPaymentConfMapper.toDto(userPaymentConf);
    }

    @Override
    public Optional<UserPaymentConfDTO> partialUpdate(UserPaymentConfDTO userPaymentConfDTO) {
        log.debug("Request to partially update UserPaymentConf : {}", userPaymentConfDTO);

        return userPaymentConfRepository
            .findById(userPaymentConfDTO.getId())
            .map(existingUserPaymentConf -> {
                userPaymentConfMapper.partialUpdate(existingUserPaymentConf, userPaymentConfDTO);

                return existingUserPaymentConf;
            })
            .map(userPaymentConfRepository::save)
            .map(userPaymentConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserPaymentConfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPaymentConfs");
        return userPaymentConfRepository.findAll(pageable).map(userPaymentConfMapper::toDto);
    }

    /**
     *  Get all the userPaymentConfs where Utilisateur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserPaymentConfDTO> findAllWhereUtilisateurIsNull() {
        log.debug("Request to get all userPaymentConfs where Utilisateur is null");
        return StreamSupport
            .stream(userPaymentConfRepository.findAll().spliterator(), false)
            .filter(userPaymentConf -> userPaymentConf.getUtilisateur() == null)
            .map(userPaymentConfMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserPaymentConfDTO> findOne(Long id) {
        log.debug("Request to get UserPaymentConf : {}", id);
        return userPaymentConfRepository.findById(id).map(userPaymentConfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserPaymentConf : {}", id);
        userPaymentConfRepository.deleteById(id);
    }
}

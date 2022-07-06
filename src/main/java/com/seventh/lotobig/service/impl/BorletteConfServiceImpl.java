package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.BorletteConf;
import com.seventh.lotobig.repository.BorletteConfRepository;
import com.seventh.lotobig.service.BorletteConfService;
import com.seventh.lotobig.service.dto.BorletteConfDTO;
import com.seventh.lotobig.service.mapper.BorletteConfMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BorletteConf}.
 */
@Service
@Transactional
public class BorletteConfServiceImpl implements BorletteConfService {

    private final Logger log = LoggerFactory.getLogger(BorletteConfServiceImpl.class);

    private final BorletteConfRepository borletteConfRepository;

    private final BorletteConfMapper borletteConfMapper;

    public BorletteConfServiceImpl(BorletteConfRepository borletteConfRepository, BorletteConfMapper borletteConfMapper) {
        this.borletteConfRepository = borletteConfRepository;
        this.borletteConfMapper = borletteConfMapper;
    }

    @Override
    public BorletteConfDTO save(BorletteConfDTO borletteConfDTO) {
        log.debug("Request to save BorletteConf : {}", borletteConfDTO);
        BorletteConf borletteConf = borletteConfMapper.toEntity(borletteConfDTO);
        borletteConf = borletteConfRepository.save(borletteConf);
        return borletteConfMapper.toDto(borletteConf);
    }

    @Override
    public BorletteConfDTO update(BorletteConfDTO borletteConfDTO) {
        log.debug("Request to save BorletteConf : {}", borletteConfDTO);
        BorletteConf borletteConf = borletteConfMapper.toEntity(borletteConfDTO);
        borletteConf = borletteConfRepository.save(borletteConf);
        return borletteConfMapper.toDto(borletteConf);
    }

    @Override
    public Optional<BorletteConfDTO> partialUpdate(BorletteConfDTO borletteConfDTO) {
        log.debug("Request to partially update BorletteConf : {}", borletteConfDTO);

        return borletteConfRepository
            .findById(borletteConfDTO.getId())
            .map(existingBorletteConf -> {
                borletteConfMapper.partialUpdate(existingBorletteConf, borletteConfDTO);

                return existingBorletteConf;
            })
            .map(borletteConfRepository::save)
            .map(borletteConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BorletteConfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BorletteConfs");
        return borletteConfRepository.findAll(pageable).map(borletteConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BorletteConfDTO> findOne(Long id) {
        log.debug("Request to get BorletteConf : {}", id);
        return borletteConfRepository.findById(id).map(borletteConfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BorletteConf : {}", id);
        borletteConfRepository.deleteById(id);
    }
}

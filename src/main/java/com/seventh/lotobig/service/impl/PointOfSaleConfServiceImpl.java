package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.PointOfSaleConf;
import com.seventh.lotobig.repository.PointOfSaleConfRepository;
import com.seventh.lotobig.service.PointOfSaleConfService;
import com.seventh.lotobig.service.dto.PointOfSaleConfDTO;
import com.seventh.lotobig.service.mapper.PointOfSaleConfMapper;
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
 * Service Implementation for managing {@link PointOfSaleConf}.
 */
@Service
@Transactional
public class PointOfSaleConfServiceImpl implements PointOfSaleConfService {

    private final Logger log = LoggerFactory.getLogger(PointOfSaleConfServiceImpl.class);

    private final PointOfSaleConfRepository pointOfSaleConfRepository;

    private final PointOfSaleConfMapper pointOfSaleConfMapper;

    public PointOfSaleConfServiceImpl(PointOfSaleConfRepository pointOfSaleConfRepository, PointOfSaleConfMapper pointOfSaleConfMapper) {
        this.pointOfSaleConfRepository = pointOfSaleConfRepository;
        this.pointOfSaleConfMapper = pointOfSaleConfMapper;
    }

    @Override
    public PointOfSaleConfDTO save(PointOfSaleConfDTO pointOfSaleConfDTO) {
        log.debug("Request to save PointOfSaleConf : {}", pointOfSaleConfDTO);
        PointOfSaleConf pointOfSaleConf = pointOfSaleConfMapper.toEntity(pointOfSaleConfDTO);
        pointOfSaleConf = pointOfSaleConfRepository.save(pointOfSaleConf);
        return pointOfSaleConfMapper.toDto(pointOfSaleConf);
    }

    @Override
    public PointOfSaleConfDTO update(PointOfSaleConfDTO pointOfSaleConfDTO) {
        log.debug("Request to save PointOfSaleConf : {}", pointOfSaleConfDTO);
        PointOfSaleConf pointOfSaleConf = pointOfSaleConfMapper.toEntity(pointOfSaleConfDTO);
        pointOfSaleConf = pointOfSaleConfRepository.save(pointOfSaleConf);
        return pointOfSaleConfMapper.toDto(pointOfSaleConf);
    }

    @Override
    public Optional<PointOfSaleConfDTO> partialUpdate(PointOfSaleConfDTO pointOfSaleConfDTO) {
        log.debug("Request to partially update PointOfSaleConf : {}", pointOfSaleConfDTO);

        return pointOfSaleConfRepository
            .findById(pointOfSaleConfDTO.getId())
            .map(existingPointOfSaleConf -> {
                pointOfSaleConfMapper.partialUpdate(existingPointOfSaleConf, pointOfSaleConfDTO);

                return existingPointOfSaleConf;
            })
            .map(pointOfSaleConfRepository::save)
            .map(pointOfSaleConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointOfSaleConfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PointOfSaleConfs");
        return pointOfSaleConfRepository.findAll(pageable).map(pointOfSaleConfMapper::toDto);
    }

    /**
     *  Get all the pointOfSaleConfs where PointOfSale is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PointOfSaleConfDTO> findAllWherePointOfSaleIsNull() {
        log.debug("Request to get all pointOfSaleConfs where PointOfSale is null");
        return StreamSupport
            .stream(pointOfSaleConfRepository.findAll().spliterator(), false)
            .filter(pointOfSaleConf -> pointOfSaleConf.getPointOfSale() == null)
            .map(pointOfSaleConfMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointOfSaleConfDTO> findOne(Long id) {
        log.debug("Request to get PointOfSaleConf : {}", id);
        return pointOfSaleConfRepository.findById(id).map(pointOfSaleConfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PointOfSaleConf : {}", id);
        pointOfSaleConfRepository.deleteById(id);
    }
}

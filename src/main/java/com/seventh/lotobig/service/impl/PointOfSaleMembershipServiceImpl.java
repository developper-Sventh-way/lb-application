package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.PointOfSaleMembership;
import com.seventh.lotobig.repository.PointOfSaleMembershipRepository;
import com.seventh.lotobig.service.PointOfSaleMembershipService;
import com.seventh.lotobig.service.dto.PointOfSaleMembershipDTO;
import com.seventh.lotobig.service.mapper.PointOfSaleMembershipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PointOfSaleMembership}.
 */
@Service
@Transactional
public class PointOfSaleMembershipServiceImpl implements PointOfSaleMembershipService {

    private final Logger log = LoggerFactory.getLogger(PointOfSaleMembershipServiceImpl.class);

    private final PointOfSaleMembershipRepository pointOfSaleMembershipRepository;

    private final PointOfSaleMembershipMapper pointOfSaleMembershipMapper;

    public PointOfSaleMembershipServiceImpl(
        PointOfSaleMembershipRepository pointOfSaleMembershipRepository,
        PointOfSaleMembershipMapper pointOfSaleMembershipMapper
    ) {
        this.pointOfSaleMembershipRepository = pointOfSaleMembershipRepository;
        this.pointOfSaleMembershipMapper = pointOfSaleMembershipMapper;
    }

    @Override
    public PointOfSaleMembershipDTO save(PointOfSaleMembershipDTO pointOfSaleMembershipDTO) {
        log.debug("Request to save PointOfSaleMembership : {}", pointOfSaleMembershipDTO);
        PointOfSaleMembership pointOfSaleMembership = pointOfSaleMembershipMapper.toEntity(pointOfSaleMembershipDTO);
        pointOfSaleMembership = pointOfSaleMembershipRepository.save(pointOfSaleMembership);
        return pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);
    }

    @Override
    public PointOfSaleMembershipDTO update(PointOfSaleMembershipDTO pointOfSaleMembershipDTO) {
        log.debug("Request to save PointOfSaleMembership : {}", pointOfSaleMembershipDTO);
        PointOfSaleMembership pointOfSaleMembership = pointOfSaleMembershipMapper.toEntity(pointOfSaleMembershipDTO);
        pointOfSaleMembership = pointOfSaleMembershipRepository.save(pointOfSaleMembership);
        return pointOfSaleMembershipMapper.toDto(pointOfSaleMembership);
    }

    @Override
    public Optional<PointOfSaleMembershipDTO> partialUpdate(PointOfSaleMembershipDTO pointOfSaleMembershipDTO) {
        log.debug("Request to partially update PointOfSaleMembership : {}", pointOfSaleMembershipDTO);

        return pointOfSaleMembershipRepository
            .findById(pointOfSaleMembershipDTO.getId())
            .map(existingPointOfSaleMembership -> {
                pointOfSaleMembershipMapper.partialUpdate(existingPointOfSaleMembership, pointOfSaleMembershipDTO);

                return existingPointOfSaleMembership;
            })
            .map(pointOfSaleMembershipRepository::save)
            .map(pointOfSaleMembershipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointOfSaleMembershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PointOfSaleMemberships");
        return pointOfSaleMembershipRepository.findAll(pageable).map(pointOfSaleMembershipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointOfSaleMembershipDTO> findOne(Long id) {
        log.debug("Request to get PointOfSaleMembership : {}", id);
        return pointOfSaleMembershipRepository.findById(id).map(pointOfSaleMembershipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PointOfSaleMembership : {}", id);
        pointOfSaleMembershipRepository.deleteById(id);
    }
}

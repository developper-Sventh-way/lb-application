package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.TicketOption;
import com.seventh.lotobig.repository.TicketOptionRepository;
import com.seventh.lotobig.service.TicketOptionService;
import com.seventh.lotobig.service.dto.TicketOptionDTO;
import com.seventh.lotobig.service.mapper.TicketOptionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TicketOption}.
 */
@Service
@Transactional
public class TicketOptionServiceImpl implements TicketOptionService {

    private final Logger log = LoggerFactory.getLogger(TicketOptionServiceImpl.class);

    private final TicketOptionRepository ticketOptionRepository;

    private final TicketOptionMapper ticketOptionMapper;

    public TicketOptionServiceImpl(TicketOptionRepository ticketOptionRepository, TicketOptionMapper ticketOptionMapper) {
        this.ticketOptionRepository = ticketOptionRepository;
        this.ticketOptionMapper = ticketOptionMapper;
    }

    @Override
    public TicketOptionDTO save(TicketOptionDTO ticketOptionDTO) {
        log.debug("Request to save TicketOption : {}", ticketOptionDTO);
        TicketOption ticketOption = ticketOptionMapper.toEntity(ticketOptionDTO);
        ticketOption = ticketOptionRepository.save(ticketOption);
        return ticketOptionMapper.toDto(ticketOption);
    }

    @Override
    public TicketOptionDTO update(TicketOptionDTO ticketOptionDTO) {
        log.debug("Request to save TicketOption : {}", ticketOptionDTO);
        TicketOption ticketOption = ticketOptionMapper.toEntity(ticketOptionDTO);
        ticketOption = ticketOptionRepository.save(ticketOption);
        return ticketOptionMapper.toDto(ticketOption);
    }

    @Override
    public Optional<TicketOptionDTO> partialUpdate(TicketOptionDTO ticketOptionDTO) {
        log.debug("Request to partially update TicketOption : {}", ticketOptionDTO);

        return ticketOptionRepository
            .findById(ticketOptionDTO.getId())
            .map(existingTicketOption -> {
                ticketOptionMapper.partialUpdate(existingTicketOption, ticketOptionDTO);

                return existingTicketOption;
            })
            .map(ticketOptionRepository::save)
            .map(ticketOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketOptions");
        return ticketOptionRepository.findAll(pageable).map(ticketOptionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketOptionDTO> findOne(Long id) {
        log.debug("Request to get TicketOption : {}", id);
        return ticketOptionRepository.findById(id).map(ticketOptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketOption : {}", id);
        ticketOptionRepository.deleteById(id);
    }
}

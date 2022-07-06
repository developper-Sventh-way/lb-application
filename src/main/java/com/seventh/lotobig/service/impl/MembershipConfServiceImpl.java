package com.seventh.lotobig.service.impl;

import com.seventh.lotobig.domain.MembershipConf;
import com.seventh.lotobig.repository.MembershipConfRepository;
import com.seventh.lotobig.service.MembershipConfService;
import com.seventh.lotobig.service.dto.MembershipConfDTO;
import com.seventh.lotobig.service.mapper.MembershipConfMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MembershipConf}.
 */
@Service
@Transactional
public class MembershipConfServiceImpl implements MembershipConfService {

    private final Logger log = LoggerFactory.getLogger(MembershipConfServiceImpl.class);

    private final MembershipConfRepository membershipConfRepository;

    private final MembershipConfMapper membershipConfMapper;

    public MembershipConfServiceImpl(MembershipConfRepository membershipConfRepository, MembershipConfMapper membershipConfMapper) {
        this.membershipConfRepository = membershipConfRepository;
        this.membershipConfMapper = membershipConfMapper;
    }

    @Override
    public MembershipConfDTO save(MembershipConfDTO membershipConfDTO) {
        log.debug("Request to save MembershipConf : {}", membershipConfDTO);
        MembershipConf membershipConf = membershipConfMapper.toEntity(membershipConfDTO);
        membershipConf = membershipConfRepository.save(membershipConf);
        return membershipConfMapper.toDto(membershipConf);
    }

    @Override
    public MembershipConfDTO update(MembershipConfDTO membershipConfDTO) {
        log.debug("Request to save MembershipConf : {}", membershipConfDTO);
        MembershipConf membershipConf = membershipConfMapper.toEntity(membershipConfDTO);
        membershipConf = membershipConfRepository.save(membershipConf);
        return membershipConfMapper.toDto(membershipConf);
    }

    @Override
    public Optional<MembershipConfDTO> partialUpdate(MembershipConfDTO membershipConfDTO) {
        log.debug("Request to partially update MembershipConf : {}", membershipConfDTO);

        return membershipConfRepository
            .findById(membershipConfDTO.getId())
            .map(existingMembershipConf -> {
                membershipConfMapper.partialUpdate(existingMembershipConf, membershipConfDTO);

                return existingMembershipConf;
            })
            .map(membershipConfRepository::save)
            .map(membershipConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MembershipConfDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MembershipConfs");
        return membershipConfRepository.findAll(pageable).map(membershipConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MembershipConfDTO> findOne(Long id) {
        log.debug("Request to get MembershipConf : {}", id);
        return membershipConfRepository.findById(id).map(membershipConfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MembershipConf : {}", id);
        membershipConfRepository.deleteById(id);
    }
}

package com.example.library.fine;

import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.patron.Patron;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FineService {
    FineRepository fineRepository;
    FineMapper fineMapper;

    public FineResponse create(FineRequest fineRequest) {
        Fine fine = fineMapper.toFine(fineRequest);
        return fineMapper.toFineResponse(fineRepository.save(fine));
    }

    public FineResponse updateFine(String id, FineRequest request) {
        Fine fine = fineRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_FOUND));
        fineMapper.updateFine(fine, request);
        return fineMapper.toFineResponse(fineRepository.save(fine));
    }

    public Page<FineResponse> getFines( Pageable pageable) {
        Page<Fine> fines;
        fines = fineRepository.findAll(pageable);
        return fines.map(fineMapper::toFineResponse);
    }

    public void deleteFine(String id) {
        fineRepository.deleteById(id);
    }
}

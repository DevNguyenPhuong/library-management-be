package com.example.library.fine;

import com.example.library.dto.fine.FineRequest;
import com.example.library.dto.fine.FineResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

}

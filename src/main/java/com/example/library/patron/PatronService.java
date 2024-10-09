package com.example.library.patron;

import com.example.library.dto.patron.PatronRequest;
import com.example.library.dto.patron.PatronResponse;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PatronService {
    PatronRepository patronRepository;
    PatronMapper patronMapper;

    public PatronResponse create(PatronRequest request) {
        Patron patron = patronMapper.toPatron(request);

        try {
            patron = patronRepository.save(patron);
        }
        catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }


        return patronMapper.toPatronResponse(patron);
    }

    public List<PatronResponse> getAll() {
        var patrons  = patronRepository.findAll();
        return patrons.stream().map(patronMapper::toPatronResponse).toList();
    }

    public PatronResponse getDetails(String id){
        var patron = patronRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return patronMapper.toPatronResponse(patron);
    }

    public PatronResponse updatePatron(String id, PatronRequest request) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        patronMapper.updatePatron(patron, request);
        return patronMapper.toPatronResponse(patronRepository.save(patron));
    }

    public void deletePatron(String id) {
        patronRepository.deleteById(id);
    }
}

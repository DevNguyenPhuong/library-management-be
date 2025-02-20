package com.example.library.Shopping_session;

import com.example.library.constant.ShoppingSessionStatus;
import com.example.library.dto.ShoppingSession.ShoppingSessionCreationRequest;
import com.example.library.dto.ShoppingSession.ShoppingSessionResponse;
import com.example.library.dto.ShoppingSession.ShoppingSessionUpdateRequest;
import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import com.example.library.patron.Patron;
import com.example.library.patron.PatronRepository;
import com.example.library.patron.PatronService;
import com.example.library.user.User;
import com.example.library.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingSessionService {
    ShoppingSessionRepository shoppingSessionRepository;
    ShoppingSessionMapper shoppingSessionMapper;
    private final PatronService patronService;
    private final UserService userService;
    private final PatronRepository patronRepository;

    public ShoppingSessionResponse getShoppingSession(String sessionId) {
        ShoppingSession shoppingSession = findShoppingSession(sessionId);
        return shoppingSessionMapper.toShoppingSessionResponse(shoppingSession);
    }

    public ShoppingSessionResponse createShoppingSession(String  patronId){
        Patron patron = patronService.findPatronById(patronId);
        ShoppingSession shoppingSession = new ShoppingSession();
        shoppingSession.setStatus(ShoppingSessionStatus.IN_PROGRESS);
        shoppingSession.setPatron(patron);

        try {
            shoppingSession = shoppingSessionRepository.save(shoppingSession);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.SESSION_ALREADY_EXISTS);
        }
        return shoppingSessionMapper.toShoppingSessionResponse(shoppingSession);
    }

    public  ShoppingSessionResponse updateShoppingSession(String sessionId, ShoppingSessionUpdateRequest request){
        ShoppingSession shoppingSession = findShoppingSession(sessionId);
        shoppingSessionMapper.updateShoppingSession(shoppingSession, request);

        try {
            shoppingSession = shoppingSessionRepository.save(shoppingSession);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.SESSION_ALREADY_EXISTS);
        }
        return shoppingSessionMapper.toShoppingSessionResponse(shoppingSession);
    }

    public ShoppingSessionResponse findByPatron(String userId){
        User user = userService.findUserById(userId);

        Patron patron = patronRepository.findByUser(user).orElseThrow(() -> new AppException(ErrorCode.PATRON_NOT_FOUND));
        if(!shoppingSessionRepository.existsByPatronAndStatus(patron, ShoppingSessionStatus.IN_PROGRESS))
            return createShoppingSession(patron.getId());

        ShoppingSession shoppingSession = shoppingSessionRepository.findByPatronAndStatus(patron, ShoppingSessionStatus.IN_PROGRESS).orElseThrow(() -> new AppException(ErrorCode.SESSION_NOT_FOUND));
        return shoppingSessionMapper.toShoppingSessionResponse(shoppingSession);
    }

    public ShoppingSession findShoppingSession(String sessionId) {
        return shoppingSessionRepository.findById(sessionId).orElseThrow(() -> new AppException(ErrorCode.SESSION_NOT_FOUND));
    }


}

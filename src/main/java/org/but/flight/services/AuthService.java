package org.but.flight.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.control.Alert;
import org.but.flight.api.PassengerAuthView;
import org.but.flight.data.FlightRepository;
import org.but.flight.exceptions.ResourceNotFoundException;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class AuthService {

    private FlightRepository flightRepository;
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    public AuthService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    private PassengerAuthView findPassengerByEmail(String email) {
        return flightRepository.findPassengerByEmail(email);
    }

    public boolean authenticate(String username, String password)
    throws NoSuchAlgorithmException, InvalidKeySpecException{
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        PassengerAuthView passengerAuthView = findPassengerByEmail(username);
        if (passengerAuthView == null) {
            throw new ResourceNotFoundException("Provided email is not found.");
        }
        String strSalt = "salt";
        byte[] salt = strSalt.getBytes();
        int iterations = 10;

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        String hashedPass = toHex(skf.generateSecret(spec).getEncoded());
        String databasePassword = passengerAuthView.getPassword();

        if (!hashedPass.equals(databasePassword)) {
            return false;
        }
        return true;
    }
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}

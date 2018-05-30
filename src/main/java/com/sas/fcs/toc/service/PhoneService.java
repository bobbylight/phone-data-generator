package com.sas.fcs.toc.service;

import com.sas.fcs.toc.model.Phone;
import com.sas.fcs.toc.model.PhoneCall;
import com.sas.fcs.toc.model.PhoneType;
import com.sas.fcs.toc.repository.PhoneCallRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sas.fcs.toc.repository.PhoneRepository;

import java.util.*;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    private final PhoneCallRepository phoneCallRepository;

    private Random random;

    /**
     * Index of the next number to fetch.  Assumes this service is used by a single thread.
     */
    private int nextNumber;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneService.class);

    private static final int THIRTY_DAYS_MILLIS = 30 * 24 * 60 * 1000;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository, PhoneCallRepository phoneCallRepository) {
        this.phoneRepository = phoneRepository;
        this.phoneCallRepository = phoneCallRepository;
        random = new Random();
    }

    private Phone createPhone() {
        Phone phone = new Phone();
        phone.setNumber(createRandomPhoneNumber());
        phone.setType(createRandomPhoneType());
        return phone;
    }

    public void createAndSaveCallInformation(int count, int callCount) {

        for (int i = 0; i < count; i++) {

            String sourceNumber = getSourceNumber(callCount);
            Set<String> destNumbers = new HashSet<>();
            List<PhoneCall> phoneCalls = new ArrayList<>();

            int currentCallCount = phoneCallRepository.getCallInvolvementCount(sourceNumber);
            for (int j = currentCallCount; j < callCount; j++) {

                String destNumber = getUnusedDestNumber(sourceNumber, destNumbers);
                destNumbers.add(destNumber);

                PhoneCall call = new PhoneCall();
                call.setSourceNumber(sourceNumber);
                call.setDestNumber(destNumber);
                call.setDuration(random.nextInt(25000) + 1000L);
                call.setTime(new Date(new Date().getTime() - random.nextInt(THIRTY_DAYS_MILLIS)));
                phoneCalls.add(call);
            }

            phoneCallRepository.saveAll(phoneCalls);
            phoneCallRepository.flush();

            if ((i + 1) % 100 == 0) {
                LOGGER.info("... Created {} phone records for {} numbers", callCount, i + 1);
            }
        }
    }

    public void createAndSavePhones(int count) {

        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            phones.add(createPhone());
        }

        phoneRepository.saveAll(phones);
        phoneRepository.flush();
    }

    private String createRandomPhoneNumber() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private PhoneType createRandomPhoneType() {
        return PhoneType.values()[random.nextInt(2)];
    }

    public String getUnusedDestNumber(String sourceNumber, Set<String> destNumbers) {

        String number = phoneRepository.getNumber(nextNumber++);

        while (sourceNumber.equals(number) || destNumbers.contains(number)) {
            number = phoneRepository.getNumber(nextNumber++);
        }

        return number;
    }

    private String getSourceNumber(int callCount) {

        String sourceNumber = phoneRepository.getNumber(nextNumber++);

        // Ensure the phone number has < callCount calls
        while (phoneCallRepository.getCallInvolvementCount(sourceNumber) >= callCount) {
            sourceNumber = phoneRepository.getNumber(nextNumber++);
        }

        return sourceNumber;
    }
}

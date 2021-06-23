package com.microblink.result.extract.blinkid.generic;

import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.DriverLicenseDetailedInfo;
import com.microblink.entities.recognizers.blinkid.generic.barcode.BarcodeResult;
import com.microblink.entities.recognizers.blinkid.generic.classinfo.ClassInfo;
import com.microblink.entities.recognizers.blinkid.generic.viz.VizResult;
import com.microblink.entities.recognizers.blinkid.idbarcode.BarcodeElementKey;
import com.microblink.entities.recognizers.blinkid.idbarcode.BarcodeElements;
import com.microblink.entities.recognizers.blinkid.mrtd.MrzResult;
import com.microblink.image.Image;
import com.microblink.libutils.R;
import com.microblink.result.ResultSource;
import com.microblink.result.extract.blinkid.BlinkIdExtractor;
import com.microblink.results.date.DateResult;

public class BlinkIDCombinedRecognizerResultExtractor extends BlinkIdExtractor<BlinkIdCombinedRecognizer.Result, BlinkIdCombinedRecognizer> {
    public static String firstName;
    public static String lastName;
    public static String sex;
    public static String address;
    public static String birth;
    public static Integer age;
    public static String dateExpire;
    public static String dateExpedition;
    public static String placeBirth;
    public static String nacionality1;
    public static String maritalStatus;
    public static String documentNumber;
    public static MrzResult mrx;
    public static String documentTipe;
    public static String expedidor;
    public static String front;
    public static String back;
    public static String personal;
    //public static String fullName;






    @Override
    public boolean doesSupportResultSourceExtraction() {
        return true;
    }

    @Override
    protected void extractData(BlinkIdCombinedRecognizer.Result result) {
        extractMixedResults(result);
    }

    @Override
    protected void extractData(BlinkIdCombinedRecognizer.Result result, ResultSource resultSource) {
        switch (resultSource) {
            case FRONT:
                extractVisualResults(result.getFrontVizResult());
                break;
            case BACK:
                extractVisualResults(result.getBackVizResult());
                break;
            case MRZ:
                extractMrzResults(result.getMrzResult());
                break;
            case BARCODE:
                extractBarcodeResults(result.getBarcodeResult());
                break;
            case MIXED:
            default:
                extractMixedResults(result);
                break;
        }
    }

    private void extractMixedResults(BlinkIdCombinedRecognizer.Result result) {
        add(R.string.PPFirstName, result.getFirstName());
        add(R.string.PPLastName, result.getLastName());
        add(R.string.PPFullName, result.getFullName());
        add(R.string.PPAdditionalNameInformation, result.getAdditionalNameInformation());
        add(R.string.PPLocalizedName, result.getLocalizedName());
        add(R.string.PPSex, result.getSex());

        add(R.string.PPAddress, result.getAddress());
        add(R.string.PPAdditionalAddressInformation, result.getAdditionalAddressInformation());
        add(R.string.PPDateOfBirth, result.getDateOfBirth());
        int age = result.getAge();
        if (age != -1) {
            add(R.string.PPAge, age);
        }
        add(R.string.PPIssueDate, result.getDateOfIssue());
        add(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPDateOfExpiryPermanent, result.isDateOfExpiryPermanent());
        add(R.string.PPExpired, result.isExpired());

        add(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        add(R.string.PPNationality, result.getNationality());

        add(R.string.PPRace, result.getRace());
        add(R.string.PPReligion, result.getReligion());
        add(R.string.PPProfession, result.getProfession());
        add(R.string.PPMaritalStatus, result.getMaritalStatus());
        add(R.string.PPResidentialStatus, result.getResidentialStatus());
        add(R.string.PPEmployer, result.getEmployer());

        add(R.string.PPDocumentNumber, result.getDocumentNumber());
        add(R.string.PPPersonalNumber, result.getPersonalIdNumber());
        add(R.string.PPDocumentAdditionalNumber, result.getDocumentAdditionalNumber());
        add(R.string.PPDocumentOptionalAdditionalNumber, result.getDocumentOptionalAdditionalNumber());
        add(R.string.PPIssuingAuthority, result.getIssuingAuthority());

        DriverLicenseDetailedInfo driverLicenseInfo = result.getDriverLicenseDetailedInfo();
        if (!driverLicenseInfo.isEmpty()) {
            add(R.string.PPRestrictions, driverLicenseInfo.getRestrictions());
            add(R.string.PPEndorsements, driverLicenseInfo.getEndorsements());
            add(R.string.PPVehicleClass, driverLicenseInfo.getVehicleClass());
            add(R.string.PPConditions, driverLicenseInfo.getConditions());
        }

        ClassInfo classInfo = result.getClassInfo();
        add(R.string.PPClassInfoCountry, classInfo.getCountry().name());
        add(R.string.PPClassInfoRegion, classInfo.getRegion().name());
        add(R.string.PPClassInfoType, classInfo.getType().name());
        add(R.string.PPClassInfoCountryName, classInfo.getCountryName());
        add(R.string.PPClassInfoIsoNumericCountryCode, classInfo.getIsoNumericCountryCode());
        add(R.string.PPClassInfoIsoAlpha2CountryCode, classInfo.getIsoAlpha2CountryCode());
        add(R.string.PPClassInfoIsoAlpha3CountryCode, classInfo.getIsoAlpha3CountryCode());

        add(R.string.MBDocumentFrontImageBlurred, result.getFrontImageAnalysisResult().isBlurred());
        add(R.string.MBDocumentFrontImageColorStatus, result.getFrontImageAnalysisResult().getDocumentImageColorStatus().name());
        add(R.string.MBDocumentFrontImageMoireStatus, result.getFrontImageAnalysisResult().getDocumentImageMoireStatus().name());
        add(R.string.MBDocumentFrontImageFaceStatus, result.getFrontImageAnalysisResult().getFaceDetectionStatus().name());
        add(R.string.MBDocumentFrontImageMrzStatus, result.getFrontImageAnalysisResult().getMrzDetectionStatus().name());
        add(R.string.MBDocumentFrontImageBarcodeStatus, result.getFrontImageAnalysisResult().getBarcodeDetectionStatus().name());

        add(R.string.MBDocumentBackImageBlurred, result.getBackImageAnalysisResult().isBlurred());
        add(R.string.MBDocumentBackImageColorStatus, result.getBackImageAnalysisResult().getDocumentImageColorStatus().name());
        add(R.string.MBDocumentBackImageMoireStatus, result.getBackImageAnalysisResult().getDocumentImageMoireStatus().name());
        add(R.string.MBDocumentBackImageFaceStatus, result.getBackImageAnalysisResult().getFaceDetectionStatus().name());
        add(R.string.MBDocumentBackImageMrzStatus, result.getBackImageAnalysisResult().getMrzDetectionStatus().name());
        add(R.string.MBDocumentBackImageBarcodeStatus, result.getBackImageAnalysisResult().getBarcodeDetectionStatus().name());

        add(R.string.MBProcessingStatus, result.getProcessingStatus().name());
        add(R.string.MBRecognitionMode, result.getRecognitionMode().name());

        add(R.string.PPDocumentBothSidesMatch, result.getDocumentDataMatch().name());
    }

    private void extractVisualResults(VizResult result) {
        addIfNotEmpty(R.string.PPFirstName, result.getFirstName());
        addIfNotEmpty(R.string.PPLastName, result.getLastName());
        addIfNotEmpty(R.string.PPFullName, result.getFullName());
        addIfNotEmpty(R.string.PPAdditionalNameInformation, result.getAdditionalNameInformation());
        addIfNotEmpty(R.string.PPLocalizedName, result.getLocalizedName());
        addIfNotEmpty(R.string.PPSex, result.getSex());

        addIfNotEmpty(R.string.PPAddress, result.getAddress());
        addIfNotEmpty(R.string.PPAdditionalAddressInformation, result.getAdditionalAddressInformation());
        addIfNotEmpty(R.string.PPDateOfBirth, result.getDateOfBirth());

        addIfNotEmpty(R.string.PPIssueDate, result.getDateOfIssue());
        addIfNotEmpty(R.string.PPDateOfExpiry, result.getDateOfExpiry());
        add(R.string.PPDateOfExpiryPermanent, result.isDateOfExpiryPermanent());

        addIfNotEmpty(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        addIfNotEmpty(R.string.PPNationality, result.getNationality());

        addIfNotEmpty(R.string.PPRace, result.getRace());
        addIfNotEmpty(R.string.PPReligion, result.getReligion());
        addIfNotEmpty(R.string.PPProfession, result.getProfession());
        addIfNotEmpty(R.string.PPMaritalStatus, result.getMaritalStatus());
        addIfNotEmpty(R.string.PPResidentialStatus, result.getResidentialStatus());
        addIfNotEmpty(R.string.PPEmployer, result.getEmployer());

        addIfNotEmpty(R.string.PPDocumentNumber, result.getDocumentNumber());
        addIfNotEmpty(R.string.PPPersonalNumber, result.getPersonalIdNumber());
        addIfNotEmpty(R.string.PPDocumentAdditionalNumber, result.getDocumentAdditionalNumber());
        addIfNotEmpty(R.string.PPDocumentOptionalAdditionalNumber, result.getDocumentOptionalAdditionalNumber());
        addIfNotEmpty(R.string.PPPersonalAdditionalNumber, result.getAdditionalPersonalIdNumber());
        addIfNotEmpty(R.string.PPIssuingAuthority, result.getIssuingAuthority());
        DriverLicenseDetailedInfo driverLicenseInfo = result.getDriverLicenseDetailedInfo();

        if (!driverLicenseInfo.isEmpty()) {
            addIfNotEmpty(R.string.PPRestrictions, driverLicenseInfo.getRestrictions());
            addIfNotEmpty(R.string.PPEndorsements, driverLicenseInfo.getEndorsements());
            addIfNotEmpty(R.string.PPVehicleClass, driverLicenseInfo.getVehicleClass());
            addIfNotEmpty(R.string.PPConditions, driverLicenseInfo.getConditions());
        }
    }

    private void extractMrzResults(MrzResult result) {
        if (result.isMrzParsed()) {
            extractMRZResult(result);
        }
    }

    private void extractBarcodeResults(BarcodeResult result) {
        addIfNotEmpty(R.string.PPFirstName, result.getFirstName());
        addIfNotEmpty(R.string.PPLastName, result.getLastName());
        addIfNotEmpty(R.string.PPFullName, result.getFullName());
        addIfNotEmpty(R.string.PPMiddleName, result.getMiddleName());
        addIfNotEmpty(R.string.PPAdditionalNameInformation, result.getAdditionalNameInformation());
        addIfNotEmpty(R.string.PPSex, result.getSex());

        addIfNotEmpty(R.string.PPAddress, result.getAddress());
        addIfNotEmpty(R.string.PPCity, result.getCity());
        addIfNotEmpty(R.string.PPStreet, result.getStreet());
        addIfNotEmpty(R.string.PPPostalCode, result.getPostalCode());
        addIfNotEmpty(R.string.PPJurisdiction, result.getJurisdiction());
        addIfNotEmpty(R.string.PPDateOfBirth, result.getDateOfBirth());

        addIfNotEmpty(R.string.PPIssueDate, result.getDateOfIssue());
        addIfNotEmpty(R.string.PPDateOfExpiry, result.getDateOfExpiry());

        addIfNotEmpty(R.string.PPPlaceOfBirth, result.getPlaceOfBirth());
        addIfNotEmpty(R.string.PPNationality, result.getNationality());

        addIfNotEmpty(R.string.PPRace, result.getRace());
        addIfNotEmpty(R.string.PPReligion, result.getReligion());
        addIfNotEmpty(R.string.PPProfession, result.getProfession());
        addIfNotEmpty(R.string.PPMaritalStatus, result.getMaritalStatus());
        addIfNotEmpty(R.string.PPResidentialStatus, result.getResidentialStatus());
        addIfNotEmpty(R.string.PPEmployer, result.getEmployer());

        addIfNotEmpty(R.string.PPDocumentNumber, result.getDocumentNumber());
        addIfNotEmpty(R.string.PPPersonalNumber, result.getPersonalIdNumber());
        addIfNotEmpty(R.string.PPDocumentAdditionalNumber, result.getDocumentAdditionalNumber());
        addIfNotEmpty(R.string.PPIssuingAuthority, result.getIssuingAuthority());

        DriverLicenseDetailedInfo driverLicenseInfo = result.getDriverLicenseDetailedInfo();
        if (!driverLicenseInfo.isEmpty()) {
            addIfNotEmpty(R.string.PPRestrictions, driverLicenseInfo.getRestrictions());
            addIfNotEmpty(R.string.PPEndorsements, driverLicenseInfo.getEndorsements());
            addIfNotEmpty(R.string.PPVehicleClass, driverLicenseInfo.getVehicleClass());
            addIfNotEmpty(R.string.PPConditions, driverLicenseInfo.getConditions());
        }

        BarcodeElements extendedElements = result.getExtendedElements();
        if (!extendedElements.isEmpty()) {
            for (BarcodeElementKey key: BarcodeElementKey.values()) {
                String barcodeElement = extendedElements.getValue(key);
                if (!barcodeElement.isEmpty()) {
                    add(R.string.PPExtendedBarcodeData, key.name() + ": " + barcodeElement);
                }
            }
        }

        addIfNotEmpty(R.string.PPBarcodeType, result.getBarcodeType().name());
    }

    @Override
    protected void onDataExtractionDone(BlinkIdCombinedRecognizer.Result result, ResultSource resultSource) {



         firstName = result.getFirstName();
         lastName = result.getLastName();

         if(result.getSex().equals("MASCULINO")){
            sex = "1";
         }else if(result.getSex().equals("FEMENINO")){
            sex = "2";
         }

         age = result.getAge();
         address = result.getAddress();
         birth = result.getDateOfBirth().toString();
         documentNumber = result.getDocumentNumber();
         placeBirth = result.getPlaceOfBirth();
         nacionality1 = result.getNationality();
         dateExpire = result.getDateOfExpiry().toString();
         dateExpedition = result.getDateOfIssue().toString();

         if(result.getMaritalStatus().equals("SOLTERO") || result.getMaritalStatus().equals("SOLTERA")){
             maritalStatus = "1";
         }else if(result.getMaritalStatus().equals("CASADO") || result.getMaritalStatus().equals("CASADA")){
             maritalStatus = "2";
         }else{
             maritalStatus = "3";
         }


         documentTipe = "1";
         mrx = result.getMrzResult();
         expedidor = result.getIssuingAuthority();
         front = result.getFullDocumentFrontImage().toString();
        back = result.getFullDocumentBackImage().toString();
        personal = result.getFaceImage().toString();

     /*   System.out.println("===================================================================");
        System.out.println("===================================================================");
        System.out.println("===================================================================");
        System.out.println("===================================================================");
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(sex);
        System.out.println(age);
        System.out.println(address);
        System.out.println(birth);
        System.out.println(documentNumber);
        System.out.println(placeBirth);
        System.out.println(nacionality1);
        System.out.println(dateExpire);
        System.out.println(dateExpedition);
        System.out.println(maritalStatus);
        System.out.println(documentTipe);
        System.out.println(mrx);
        System.out.println(expedidor);
        System.out.println(front);
        System.out.println(back);
        System.out.println(personal);
*/

        if (resultSource == ResultSource.MIXED) {
            extractCommonData(result, mExtractedData, mBuilder);
        }
    }

}
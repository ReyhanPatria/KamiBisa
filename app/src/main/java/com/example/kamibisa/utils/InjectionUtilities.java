package com.example.kamibisa.utils;

import com.example.kamibisa.data.database.Database;
import com.example.kamibisa.data.repository.BloodDonationRepository;
import com.example.kamibisa.data.repository.DonationRecordRepository;
import com.example.kamibisa.data.repository.DonationRepository;
import com.example.kamibisa.data.repository.UserRepository;
import com.example.kamibisa.ui.viewmodel.factory.BloodDonationPageViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.BloodDonationViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.CreateBloodDonationViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.CreateDonationViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.DonateViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.DonationViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.EditProfileViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.HistoryViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.HomeViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.LoginViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.ProfileViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.RegisterViewModelFactory;
import com.example.kamibisa.ui.viewmodel.factory.SearchResultViewModelFactory;

public class InjectionUtilities {
    public static InjectionUtilities instance;

    public InjectionUtilities() { }

    public static InjectionUtilities getInstance() {
        if(instance == null) {
            instance = new InjectionUtilities();
        }
        return instance;
    }

    public RegisterViewModelFactory provideRegisterViewModelFactory() {
        UserRepository userRepository = UserRepository.getInstance(
                Database.getInstance().getUserDao());
        return new RegisterViewModelFactory(userRepository);
    }

    public LoginViewModelFactory provideLoginViewModelFactory() {
        UserRepository userRepository = UserRepository.getInstance(
                Database.getInstance().getUserDao());
        return new LoginViewModelFactory(userRepository);
    }

    public HomeViewModelFactory provideHomeViewModelFactory() {
        DonationRepository donationRepository = DonationRepository.getInstance(
                Database.getInstance().getDonationDao());
        return new HomeViewModelFactory(donationRepository);
    }

    public CreateDonationViewModelFactory provideCreateDonationViewModelFactory() {
        DonationRepository donationRepository = DonationRepository.getInstance(
                Database.getInstance().getDonationDao());
        return new CreateDonationViewModelFactory(donationRepository);
    }

    public DonationViewModelFactory provideDonationViewModelFactory() {
        DonationRepository donationRepository = DonationRepository.getInstance(
                Database.getInstance().getDonationDao());
        return new DonationViewModelFactory(donationRepository);
    }

    public DonateViewModelFactory provideDonateViewModelFactory() {
        DonationRepository donationRepository = DonationRepository.getInstance(
                Database.getInstance().getDonationDao()
        );
        DonationRecordRepository donationRecordRepository = DonationRecordRepository.getInstance(
                Database.getInstance().getDonationRecordDao()
        );
        return new DonateViewModelFactory(donationRepository, donationRecordRepository);
    }

    public BloodDonationViewModelFactory provideBloodDonationViewModelFactory() {
        BloodDonationRepository bloodDonationRepository = BloodDonationRepository.getInstance(
                Database.getInstance().getBloodDonationDao());
        return new BloodDonationViewModelFactory(bloodDonationRepository);
    }

    public CreateBloodDonationViewModelFactory provideCreateBloodDonationViewModelFactory() {
        BloodDonationRepository bloodDonationRepository = BloodDonationRepository.getInstance(
                Database.getInstance().getBloodDonationDao());
        return new CreateBloodDonationViewModelFactory(bloodDonationRepository);
    }

    public BloodDonationPageViewModelFactory provideBloodDonationPageViewModelFactory() {
        BloodDonationRepository bloodDonationRepository = BloodDonationRepository.getInstance(
                Database.getInstance().getBloodDonationDao());
        return new BloodDonationPageViewModelFactory(bloodDonationRepository);
    }

    public HistoryViewModelFactory provideHistoryViewModelFactory() {
        DonationRepository donationRepository = DonationRepository.getInstance(
                Database.getInstance().getDonationDao()
        );
        DonationRecordRepository donationRecordRepository = DonationRecordRepository.getInstance(
                Database.getInstance().getDonationRecordDao()
        );
        return new HistoryViewModelFactory(donationRepository, donationRecordRepository);
    }

    public ProfileViewModelFactory provideProfileViewModelFactory() {
        UserRepository userRepository = UserRepository.getInstance(
                Database.getInstance().getUserDao());
        return new ProfileViewModelFactory(userRepository);
    }

    public EditProfileViewModelFactory provideEditProfileViewModelFactory() {
        UserRepository userRepository = UserRepository.getInstance(
                Database.getInstance().getUserDao());
        return new EditProfileViewModelFactory(userRepository);
    }

    public SearchResultViewModelFactory provideSearchResultViewModelFactory() {
        DonationRepository donationRepository = DonationRepository.getInstance(
                Database.getInstance().getDonationDao());
        return new SearchResultViewModelFactory(donationRepository);
    }
}

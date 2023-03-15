
package com.dev.utils;


import com.dev.models.MyProductsModel;
import com.dev.models.UserForAdminModel;
import com.dev.objects.Product;
import com.dev.objects.User;

import com.dev.models.MyOfferModel;
import com.dev.objects.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dev.utils.Constants.ADD_AUCTION_COST;
import static com.dev.utils.Constants.CLOSE_AUCTION_COST;

@Component
public class Persist {

    private final SessionFactory sessionFactory;
    @Autowired
    private Utils utils;

    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public void initBasicDetails() {
        setAdmin();
    }

    public void setAdmin() {
        if (getAdmin() == null){
            addUser("admin", "123456", true);
        }
    }

    public User getUserByUsername(String username) {
        User found;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username")
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return found;
    }

    public void saveUser(User user) {
        Session session = sessionFactory.openSession();
        session.save(user);
        session.close();
    }

    public void saveProduct(Product product) {
        Session session = sessionFactory.openSession();
        session.save(product);
        session.close();
    }

    public void saveAuction(Auction auction) {
        Session session = sessionFactory.openSession();
        saveProduct(auction.getProductObj());
        session.save(auction);
        session.close();
    }

    public void saveOffer(Offer offer) {
        Session session = sessionFactory.openSession();
        session.save(offer);
        session.close();
    }

    public List<UserForAdminModel> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> allUsers = session.createQuery("FROM User WHERE isAdmin = :isAdmin")
                .setParameter("isAdmin", false).list();
        session.close();
        return allUsers.stream()
                .map(user -> UserForAdminModel.builder()
                        .credit(user.roundAvoid(user.getCredit(), 2))
                        .username(user.getUsername())
                        .token(user.getToken())
                        .sumAuctions(getSumAuctions(user.getToken()))
                        .build())
                .collect(Collectors.toList());
    }

    public Integer getSumAuctions(String token) {
        Session session = sessionFactory.openSession();
        Integer sumAuctions = session.createQuery("From Auction WHERE productObj.owner.token = :token")
                .setParameter("token", token)
                .list().size();
        session.close();
        return sumAuctions;
    }

    public User getUserByToken(String token) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("From User WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return user;
    }

    public void addUser(String userName, String password, boolean isAdmin) {
        String token = utils.createHash(userName, password);
        User userObject = new User(userName, token, isAdmin);
        saveUser(userObject);
    }

    public List<MyProductsModel> getMyProducts(String token) {
        Session session = sessionFactory.openSession();
        List<Auction> myAuctions = session.createQuery("FROM Auction WHERE productObj.owner.token = :token")
                .setParameter("token", token).list();
        session.close();
        List<MyProductsModel> myProducts = new ArrayList<>();
        myAuctions.forEach(auction -> {
            List<Offer> auctionOffers = getOffersByAuctionID(auction.getId());
            myProducts.add(new MyProductsModel(auction, auctionOffers));
        });
        return myProducts;
    }

    public List<MyOfferModel> getMyOffers(String token) {
        Session session = sessionFactory.openSession();
        List<Offer> myOffers = session.createQuery("FROM Offer where offers.token = :token")
                .setParameter("token", token).list();
        session.close();
        List<MyOfferModel> myOffersModel = new ArrayList<>();
        myOffers.forEach(offer -> {
            List<Offer> auctionOffers = getOffersByAuctionID(offer.getAuction().getId());
            myOffersModel.add(new MyOfferModel(offer, auctionOffers));
        });
        return myOffersModel;
    }

    public List<Offer> getOffersByAuctionID(int auctionID) {
        Session session = sessionFactory.openSession();
        List<Offer> offers = session.createQuery("FROM Offer where auction.id= :auctionID")
                .setParameter("auctionID", auctionID).list();
        session.close();
        return offers;
    }

    public Auction getAuctionByProductID(int productID) {
        Session session = sessionFactory.openSession();
        Auction auction = (Auction) session.createQuery("FROM Auction where productObj.id= :productID")
                .setParameter("productID", productID).uniqueResult();
        session.close();
        return auction;
    }

    public void makeNewOffer(User user, int amount, Auction auction, Double credit) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Offer newOffer = new Offer(amount, user, auction);
        saveOffer(newOffer);
        updateUserCredit(user, credit);
        transaction.commit();
        session.close();
    }

    public synchronized void updateUserCredit(User user, Double credit) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        user.setCredit(credit);
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public Integer getNumberOfUsers() {
        Session session = sessionFactory.openSession();
        Integer numOfUsers = session.createQuery("From User where isAdmin = :admin")
                .setParameter("admin", false)
                .list().size();
        session.close();
        return numOfUsers;
    }

    public Integer getNumberOfOffers() {
        Session session = sessionFactory.openSession();
        List<Offer> numOfOffers = session.createQuery("FROM Offer ").list();
        session.close();
        return numOfOffers.size();
    }

    public Integer getNumberOfAuctions() {
        Session session = sessionFactory.openSession();
        Integer numOfAuctions = session.createQuery("FROM Auction").list().size();
        session.close();
        return numOfAuctions;
    }

    public List<Auction> getAllOpenAuctions() {
        Session session = sessionFactory.openSession();
        List<Auction> auctions = session.createQuery("FROM Auction where isOpen = :open")
                .setParameter("open", true).list();
        session.close();
        return auctions;
    }

    public boolean checkOwnerOfAuctionByUserID(int ownerID, int productID) {
        Session session = sessionFactory.openSession();
        Product product = (Product) session.createQuery("FROM Product where owner.id = :ownerID and id = :productID")
                .setParameter("ownerID", ownerID)
                .setParameter("productID", productID).uniqueResult();
        session.close();
        return product != null;
    }

    public void closeAuction(Auction auction) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        auction.setIsOpen(false);
        session.saveOrUpdate(auction);
        transaction.commit();
        session.close();
    }

    public synchronized void payForSystem(double pay) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User admin = getAdmin();
        admin.setCredit(admin.getCredit() + pay);
        session.saveOrUpdate(admin);
        transaction.commit();
        session.close();
    }

    private User getAdmin() {
        Session session = sessionFactory.openSession();
        User admin = (User) session.createQuery("FROM User WHERE isAdmin = :isAdmin")
                .setParameter("isAdmin", true).uniqueResult();
        session.close();
        return admin;
    }

    public void updateCreditsForNewAuction(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        double newCredit = user.getCredit() - ADD_AUCTION_COST;
        payForSystem(ADD_AUCTION_COST);
        updateUserCredit(user, newCredit);
        transaction.commit();
        session.close();
    }

    public void updateCredits(User user, Auction auction) {
        List<Offer> auctionOffers = getOffersByAuctionID(auction.getId());
        Offer winOffer = auction.getWinnerOffer(auctionOffers);
        int winOfferAmount = winOffer.getAmount();
        double costForSystem = winOfferAmount * CLOSE_AUCTION_COST;
        double newCredit = user.getCredit() + winOfferAmount - costForSystem;
        payForSystem(costForSystem);
        updateUserCredit(user, newCredit);
        List<User> usersForRefund = auctionOffers.stream()
                .map(Offer::getOffers)
                .filter(offers -> !offers.equals(winOffer.getOffers()))
                .distinct().collect(Collectors.toList());
        usersForRefund.forEach(user1 -> updateUserCredit(user1, getRefundCredit(user1, auctionOffers)));
    }

    private Double getRefundCredit(User user, List<Offer> auctionOffers) {
        Optional<Integer> lastAmount = this.lastOfferAmount(user, auctionOffers);
        return user.getCredit() + (lastAmount.orElse(0));
    }

    public Optional<Integer> lastOfferAmount(User user, List<Offer> auctionOffers) {
        return auctionOffers.stream()
                .filter(offer -> offer.getOffers().equals(user))
                .map(Offer::getAmount).max(Integer::compareTo);
    }
}

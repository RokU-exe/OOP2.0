package utils;

import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.lyyhbfqsjhcujgbjkqlc";
    private static final String PASSWORD = "9FCf7nrJcs7Qwz5E";

//    Bao Do's Database
//    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:5432/postgres";
//    private static final String USER = "postgres.xjekduuxxczbrnzmdhzo";
//    private static final String PASSWORD = "giabaodoxuan";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static User getUserByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void addUser(User user) {
        String query = "INSERT INTO users (id, full_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getRole().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User user) {
        String query = "UPDATE users SET full_name = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRole().name());
            pstmt.setString(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String userId) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getFilteredCustomers(String filterCriteria) {
        List<User> customers = new ArrayList<>();
        String query = "SELECT * FROM users WHERE " + filterCriteria;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new User(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        UserRole.valueOf(rs.getString("role"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static List<Claim> getFilteredClaims(/*String filterCriteria*/) {
        List<Claim> claims = new ArrayList<>();
        String query = "SELECT claims.* FROM claims INNER JOIN users ON claims.insured_person = users.full_name"; /*filterCriteria;*/
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                claims.add(new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("card_number"),
                        rs.getDate("exam_date"),
                        null,
                        rs.getDouble("claim_amount"),
                        ClaimStatus.valueOf(rs.getString("status")),
                        rs.getString("receiver_bank"),
                        rs.getString("receiver_name"),
                        rs.getString("receiver_number"),
                        rs.getString("policyHolder_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claims;
    }

    public static void updateClaim(Claim claim) {
        String query = "UPDATE claims SET claim_date = ?, insured_person = ?, card_number = ?, exam_date = ?, " +
                "claim_amount = ?, status = ?, receiver_bank = ?, receiver_name = ?, receiver_number = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(claim.getClaimDate().getTime()));
            pstmt.setString(2, claim.getInsuredPerson());
            pstmt.setString(3, claim.getCardNumber());
            pstmt.setDate(4, new java.sql.Date(claim.getExamDate().getTime()));
            pstmt.setDouble(5, claim.getClaimAmount());
            pstmt.setString(6, claim.getStatus().name());
            pstmt.setString(7, claim.getReceiverBank());
            pstmt.setString(8, claim.getReceiverName());
            pstmt.setString(9, claim.getReceiverNumber());
            pstmt.setString(10, claim.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double calculateTotalClaimedAmount() {
        double totalClaimedAmount = 0;
        String query = "SELECT SUM(claim_amount) AS total FROM claims WHERE status = 'DONE'";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                totalClaimedAmount = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalClaimedAmount;
    }
    public static List<Claim> getAllClaims() {
        List<Claim> claims = new ArrayList<>();
        String query = "SELECT * FROM claims";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                claims.add(new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("card_number"),
                        rs.getDate("exam_date"),
                        null,
                        rs.getDouble("claim_amount"),
                        ClaimStatus.valueOf(rs.getString("status")),
                        rs.getString("receiver_bank"),
                        rs.getString("receiver_name"),
                        rs.getString("receiver_number"),
                        rs.getString("policy_holder_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claims;
    }

    public static void approveClaim(String claimId) {
        String query = "UPDATE claims SET status = 'APPROVED' WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, claimId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rejectClaim(String claimId) {
        String query = "UPDATE claims SET status = 'REJECTED' WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, claimId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Additional methods for ManagerController
    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static List<Surveyor> getAllSurveyors() {
        List<Surveyor> surveyors = new ArrayList<>();
        String query = "SELECT * FROM surveyors";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                surveyors.add(new Surveyor(
                        rs.getString("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return surveyors;
    }
     public static String getPrefixByUserRole(UserRole role) {
        switch (role) {
            case POLICY_HOLDER:
                return "h";
            case POLICY_OWNER:
                return "o";
            case INSURANCE_SURVEYOR:
                return "s";
            case INSURANCE_MANAGER:
                return "m";
            case DEPENDENT:
                return "d";
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

  public static String getLargestIdByUserRole(UserRole role) {
        String prefix = getPrefixByUserRole(role);
        String query = "SELECT MAX(CAST(SUBSTRING(id FROM LENGTH(?) + 1) AS INTEGER)) AS max_id " +
                "FROM users " +
                "WHERE role = ? AND SUBSTRING(id FROM LENGTH(?) + 1) ~ '^[0-9]+$'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, prefix);
            pstmt.setString(2, role.toString());
            pstmt.setString(3, prefix);
            ResultSet rs = pstmt.executeQuery();
            int maxId = 0; // Default maxId if no records found
            if (rs.next()) {
                // Get the max_id from the result set
                maxId = rs.getInt("max_id");
                // Check if max_id was null (i.e., no records found)
                if (rs.wasNull()) {
                    maxId = 0; // No records found, so initialize to 0
                }
            }
            // Increment the numeric part of the ID
            maxId++;
            // Format the new ID with the prefix and zero-padded numeric part
            String largestId = String.format("%s%02d", prefix, maxId);
            return largestId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
public static String generateUniqueRandomCardNumber() {
        int numRetries = 0; // Track retry attempts
        int maxRetries = 10; // Set a maximum number of retries (optional)
        String cardNumber;
        do {
            cardNumber = generateRandomCardNumber();
            if (isCardNumberInUse(cardNumber)) {
                numRetries++;
                if (numRetries >= maxRetries) {
                    throw new RuntimeException("Failed to generate unique card number after " + maxRetries + " attempts.");
                }
            }
        } while (isCardNumberInUse(cardNumber));
        return cardNumber;
    }
    public static String generateRandomCardNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static boolean isCardNumberInUse(String cardNumber) {
        String query = "SELECT COUNT(*) FROM \"InsuranceCard\" WHERE card_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Check if count is greater than 0 (card exists)
            } else {
                // Table might be empty, consider this a unique card number
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors (consider logging or throwing exception)
            return false; // Assume failure on error
        }
    }
    public static void addInsuranceCard(InsuranceCard card) {
        String query = "INSERT INTO \"InsuranceCard\" (card_number, policy_holder_id, policy_owner_id, expire_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, card.getCardNumber()); // Use getters from the object
            pstmt.setString(2, card.getCardHolder());
            pstmt.setString(3, card.getPolicyOwner()); // Assuming policyOwnerId exists
            java.util.Date utilDate = card.getExpirationDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            pstmt.setDate(4, sqlDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getPolicyHolders() throws SQLException {
        List<String> policyHolders = new ArrayList<>();
        String query = "SELECT u.id, u.full_name " +
                "FROM users u " +
                "LEFT JOIN \"InsuranceCard\" ic ON u.id = ic.policy_holder_id " +
                "WHERE ic.policy_holder_id IS NULL AND u.role = 'POLICY_HOLDER'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                String id = rs.getString("id");
                policyHolders.add(fullName + " - " + id);
            }
        }
        return policyHolders;
    }

    // Method to retrieve policy owners from the database
    public static List<String> getPolicyOwners() throws SQLException {
        List<String> policyOwners = new ArrayList<>();
        String query = "SELECT u.id, u.full_name " +
                "FROM users u " +
                "WHERE u.role = 'POLICY_OWNER'";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String fullName = rs.getString("full_name");
                String id = rs.getString("id");
                policyOwners.add(fullName + " - " + id);
            }
        }
        return policyOwners;
    }

    //get claim for PO
    public static List<Claim> getClaimsForPolicyOwner() throws SQLException {
        List<Claim> claims = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            String query = "SELECT claims.* FROM claims JOIN insurancecard i ON claims.cardNumber = i.cardNumber " +
                    "JOIN users u ON i.policyHolderId = u.id";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("No claims found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to propagate it
        } finally {
            // Close resources in reverse order of creation
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return claims;
    }


    //Use to review claim (Insurance Surveyor) before decide to propose to manager or require more information
    public static List<Claim> surveyorReviewClaim(){
        List<Claim> claims = new ArrayList<>();
        String query = "SELECT * FROM claims WHERE status = 'NEW'";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                claims.add(new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("card_number"),
                        rs.getDate("exam_date"),
                        null,
                        rs.getDouble("claim_amount"),
                        ClaimStatus.valueOf(rs.getString("status")),
                        rs.getString("receiver_bank"),
                        rs.getString("receiver_name"),
                        rs.getString("receiver_number"),
                        rs.getString("policyHolder_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return claims;
    }

    //Use to propose claim to manager (Insurance Surveyor) STILL ERROR NHA :)))))
    public static List<Claim> surveyorProposeToManager(){
        List<Claim> claims = new ArrayList<>();
        String query = "UPDATE claims SET status = 'PROCESSING' WHERE status = 'NEW'";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                claims.add(new Claim(
                        rs.getString("id"),
                        rs.getDate("claim_date"),
                        rs.getString("insured_person"),
                        rs.getString("card_number"),
                        rs.getDate("exam_date"),
                        null,
                        rs.getDouble("claim_amount"),
                        ClaimStatus.valueOf(rs.getString("status")),
                        rs.getString("receiver_bank"),
                        rs.getString("receiver_name"),
                        rs.getString("receiver_number"),
                        rs.getString("policyHolder_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }
    public static void approveClaim(int claimId) {
        try (Connection conn = connect()) {
            String sql = "UPDATE claims SET status = 'approved' WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, claimId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rejectClaim(int claimId) {
        try (Connection conn = connect()) {
            String sql = "UPDATE claims SET status = 'rejected' WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, claimId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Claim> getClaimsToExamine() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT * FROM claims WHERE status = 'waiting for examination'";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Claim claim = new Claim(
                        rs.getInt("id"),
                        rs.getString("policy_holder_name"),
                        rs.getString("status")
                );
                claims.add(claim);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return claims;
    }
}

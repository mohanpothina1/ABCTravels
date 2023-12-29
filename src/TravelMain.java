import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

    public class TravelMain {

        private static List<User> users = new ArrayList<>();

        private static Map<String, Integer> userInvalidLoginAttempt = new HashMap<>();

        private static List<Order> orders = new ArrayList<>();
        private static List<Vehicle> vehicles = new ArrayList<>();
        private static List<Route> routes = new ArrayList();

        public static void main(String[] args) throws IOException {
            Vehicle vehicle = new Vehicle("VNo1", "Bus", 40, "7989", null);
//        routes.add(new Route("Hyd", "Vja", LocalDate.now(), 450.0, 12));
//        routes.add(new Route("Vzg", "Vja", LocalDate.now(), 475.0, 6));
            routes.add(new Route(1, "Hyd", "Goa", LocalDate.parse("2023-12-31", DateTimeFormatter.ISO_LOCAL_DATE), 1475.25, 25, null));
            routes.add(new Route(1, "Hyd", "Goa", LocalDate.parse("2023-12-30", DateTimeFormatter.ISO_LOCAL_DATE), 1475.25, 25, null));
            routes.add(new Route(2, "Mumbai", "Bangalore", LocalDate.parse("2024-01-01", DateTimeFormatter.ISO_LOCAL_DATE), 2475.0, 6, null));
//        routes.add(new Route("Delhi", "Gujarat", LocalDate.parse("2023-12-31", DateTimeFormatter.ISO_LOCAL_DATE), 1475.0, 6));
//        routes.add(new Route("Pune", "Odisha", LocalDate.parse("2023-12-31", DateTimeFormatter.ISO_LOCAL_DATE), 1475.0, 6));
            vehicle.routes = routes;
            if (displayCompanyLogo()) {
                showMenuOptions();
            } else {
                System.out.println("Failed to load company logo existing");
            }
            System.out.println("users: " + users);

        }


        private static boolean displayCompanyLogo() throws IOException {
            String line = "C:\\mohan\\file.txt";
            System.out.println("file path");

            try (BufferedReader br = new BufferedReader(new FileReader(line))) {
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                return true; //logo loaded successfully
            } catch (IOException e) {
                System.out.println("Error loading company logo." + e.getMessage());
            }
            return false; //logo loading failed

        }

        private static void showMenuOptions() {
            Scanner sc = new Scanner(System.in);

            while (true) {

                System.out.println("\n Menu Options");
                System.out.println("1. New Admin User Registration ");
                System.out.println("2. Login");
                System.out.println("3. Plan Journey");
                System.out.println("4. Exist");

                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        registerNewAdmin();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        planJourney();
                        break;
                    case 4:
                        reScheduleJourney();
                        break;
                    case 5:
                        System.out.println("Existing the application. Thank You!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");

                }
            }
        }

        private static void reScheduleJourney() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("ReSchedule Journey");
            System.out.println("Enter OrderId : ");

            Integer orderIdToChangeDate = Integer.valueOf(scanner.nextLine());
            System.out.println("Enter which date you want to change : ");
            String changedJourneyDate = scanner.nextLine();
            LocalDate changedJourneyLocalDate = LocalDate.parse(changedJourneyDate, DateTimeFormatter.ISO_LOCAL_DATE);

        }

        private static Order planJourney() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n Plan Journey");

            System.out.print("Enter Source: ");
            String source = scanner.nextLine();
            if (source == null || source.isEmpty()) {
                System.out.println("Please enter source");
                return null;
            }

            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine();
            LocalDate plannedJourneyDate = null;
            if (destination == null || destination.isEmpty()) {
                System.out.println("Please enter destination");
                return null;
            }
            System.out.print("Enter Journey Date (YYYY-MM-DD):");
            String journeyDateString = scanner.nextLine();
            if (journeyDateString == null || journeyDateString.isEmpty()) {
                System.out.println("Please provide date");
                return null;
            } else {
                //convert string data to localdate
                plannedJourneyDate = LocalDate.parse(journeyDateString, DateTimeFormatter.ISO_LOCAL_DATE);
            }
            System.out.print("Enter Number Of Passengers: ");
            Integer noOfPassengers = Integer.valueOf(scanner.nextLine());

            System.out.println("Receive your details and please wait while we process the availability");

            boolean isRouteAvailableForPlannedJourneyDate = false;
            boolean isRouteAvailableForPlannedJourneyDate1;
            List<Route> matchingPlanJourneyRoutes1 = new ArrayList<>();

            for (Route route : routes) {
                if (route.source.equals(source)) {

                    if (route.destination.equals(destination)) {
//                    System.out.println("Route is available");
                        if (!(route.noOfSeatAvailable > 0)) {
//                        System.out.println("Sorry currently seats not available for this route : " + source);
                        } else {
//                        System.out.println("Number of seats : " + route.noOfSeatAvailable);
                            if (route.journeyDate.equals(plannedJourneyDate)) {
//                            System.out.println("This Journey Date is Available");
                                isRouteAvailableForPlannedJourneyDate1 = true;
                                matchingPlanJourneyRoutes1.add(route);
                                double bookingCost = route.ticketPrice * noOfPassengers;
                                if (plannedJourneyDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || plannedJourneyDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                                    bookingCost = bookingCost + 200;
                                    bookingCost = (bookingCost * 10 / 100) + bookingCost;
                                }
//                            System.out.println("Booking Cost for this route : " + bookingCost);
                            }
                        }
                    } else {
//                    System.out.println("Sorry Currently Route Destination Is Not Available");
                    }

                } else {
//                System.out.println("Sorry Currently Route Source Is Not Available");
                }
            }
            List<Route> matchingPlanJourneyRoutes = matchingPlanJourneyRoutes1;
            System.out.println("Available matching route for given data : " + matchingPlanJourneyRoutes);
            System.out.println("Enter the route number to confirm the booking : ");
            Integer routeNumber = Integer.valueOf(scanner.nextLine());
            Order order = null;
            for (Route route : matchingPlanJourneyRoutes) {
                if (route.routeId.equals(routeNumber)) {
                    Order order1 = new Order();
                    double bookingCost = route.ticketPrice * noOfPassengers;
                    if (plannedJourneyDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || plannedJourneyDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        bookingCost = bookingCost + 200;
                        bookingCost = (bookingCost * 10 / 100) + bookingCost;
                    }
                    System.out.println("Booking Cost for this route : " + bookingCost);
                    order1.orderAmount = bookingCost;
                    order1.orderId = 1;
                    order1.setRoute(route);
                    order1.setRequestedJourneyPlan(null);
                    order1.setOrderStatus("Created");
                    System.out.println("Your Booking Details are : " + order1);
                    System.out.println("Press 1 to confirm for payment : ");
                    Integer confirm = Integer.valueOf(scanner.nextLine());
                    if (confirm == 1) {
                        order1.setPaymentDone(true);
                        route.noOfSeatAvailable = route.noOfSeatAvailable - noOfPassengers;
                        order1.setOrderStatus("Confirmed");
                    }
                    System.out.println("Booking Cost for this route : " + order1);
                    System.out.println("Happy Journey");
                }
            }
            return null;
        }

        private static User login() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n User login");

            System.out.println("Enter username: ");
            String username = scanner.nextLine();

            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            for (User user : users) {
                Integer existingCount = userInvalidLoginAttempt.get(user.email);
                int count = existingCount != null ? existingCount : 0;
                if (count > 5) {
                    //lock user account
                    System.out.println("User with emailId:: " + user.email + " account is locked");
                    return null;
                }
                if (user.email.equals(username)) {
                    if (password.equals(user.password)) {
                        //password matches
                        System.out.println("login successful");
                        return user;
                    } else {
                        userInvalidLoginAttempt.put(user.email, ++count);
                        System.out.println("Invalid credentials::attempt:: " + count + " :: for username :: " + username);
                        //username and password not matching
                        return null;
                    }
                } else {
                    System.out.println("User not found with " + username);
                }
            }
            System.out.println("no user found in the system with username:: " + username);
            return null;
        }

        private static void registerNewAdmin() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n New Admin User Registration");

            System.out.print("Enter first name : ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name : ");
            String lastName = scanner.nextLine();

            System.out.print("Enter mobile number : ");
            String number = scanner.nextLine();

            System.out.print("Enter gender : ");
            String gender = scanner.nextLine();

            System.out.print("Enter email : ");
            String email = scanner.nextLine();

            System.out.print("Enter password : ");
            String password = scanner.nextLine();

            //for simplicity, assuming initial values for failedCount and accountStatus

            int failedCount = 0;
            String accountStatus = "Active";

            User newUser = new User(firstName, lastName, number, gender, email, password, failedCount, accountStatus);
            try{
                newUser.isUserExists();
            }catch (UserAlreadyExistsException e){
                System.out.println("User with this email::"+  newUser.email  +" already exists!");
                return;
            }
            users.add(newUser);

            System.out.println("Registration successful..!");


        }

        private static class Journey {
            private String source;
            private String destination;
            private LocalDate journeyDate;
            private int numberOfPassengers;
        }

        private static class User {
            private String firstName;
            private String lastName;
            private String number;
            private String gender;
            private String email;
            private String password;
            private int failedCount;
            private String accountStatus;

            public User(String firstName, String lastName, String number, String gender, String email, String password, int failedCount, String accountStatus) {

                this.firstName = firstName;
                this.lastName = lastName;
                this.number = number;
                this.gender = gender;
                this.email = email;
                this.password = password;
                this.failedCount = failedCount;
                this.accountStatus = accountStatus;
            }

            public void isUserExists() {
                for (User existingUser : users) {
                    if (existingUser.email.equals(this.email)) {
                        throw new UserAlreadyExistsException();
                    }
                }

            }
        }

        private static class UserAlreadyExistsException extends RuntimeException{}
        private static class Route {

            private Integer routeId;
            private String source;
            private String destination;
            private LocalDate journeyDate;
            private double ticketPrice;
            private int noOfSeatAvailable;
            private List<Vehicle> vehiclesMappedToThisRoute = new ArrayList<>();

            public Route(Integer routeId, String source, String destination, LocalDate journeyDate, double ticketPrice, int noOfSeatAvailable, List<Vehicle> vehiclesMappedToThisRoute) {
                this.routeId = routeId;
                this.source = source;
                this.destination = destination;
                this.journeyDate = journeyDate;
                this.ticketPrice = ticketPrice;
                this.noOfSeatAvailable = noOfSeatAvailable;
                this.vehiclesMappedToThisRoute = vehiclesMappedToThisRoute;
            }

            @Override
            public String toString() {
                return "Route{" +
                        "routeId=" + routeId +
                        ", source='" + source + '\'' +
                        ", destination='" + destination + '\'' +
                        ", journeyDate=" + journeyDate +
                        ", ticketPrice=" + ticketPrice +
                        ", noOfSeatAvailable=" + noOfSeatAvailable +
                        ", vehiclesMappedToThisRoute=" + vehiclesMappedToThisRoute +
                        '}';
            }
        }

        private static class Order {
            private Integer orderId;
            private Route route;
            private Journey requestedJourneyPlan;
            private Double orderAmount;
            private boolean isPaymentDone;
            private String orderStatus;

            public Integer getOrderId() {
                return orderId;
            }

            public void setOrderId(Integer orderId) {
                this.orderId = orderId;
            }

            public Route getRoute() {
                return route;
            }

            public void setRoute(Route route) {
                this.route = route;
            }

            public Journey getRequestedJourneyPlan() {
                return requestedJourneyPlan;
            }

            public void setRequestedJourneyPlan(Journey requestedJourneyPlan) {
                this.requestedJourneyPlan = requestedJourneyPlan;
            }

            public Double getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(Double orderAmount) {
                this.orderAmount = orderAmount;
            }

            public boolean isPaymentDone() {
                return isPaymentDone;
            }

            public void setPaymentDone(boolean paymentDone) {
                isPaymentDone = paymentDone;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }


            @Override
            public String toString() {
                return "Order{" +
                        "orderId=" + orderId +
                        ", route=" + route +
                        ", requestedJourneyPlan=" + requestedJourneyPlan +
                        ", orderAmount=" + orderAmount +
                        ", isPaymentDone=" + isPaymentDone +
                        ", orderStatus='" + orderStatus + '\'' +
                        '}';
            }
        }

        private static class Vehicle {
            private String RegistrationNumber;
            private String vehicleType;
            private Integer noOfSeats = 40;
            private String serviceNumber;
            List<Route> routes = new ArrayList<>();

            public Vehicle(String registrationNumber, String vehicleType, Integer noOfSeats, String serviceNumber, List<Route> routes) {
                RegistrationNumber = registrationNumber;
                this.vehicleType = vehicleType;
                this.noOfSeats = noOfSeats;
                this.serviceNumber = serviceNumber;
                this.routes = routes;
            }

            public String getRegistrationNumber() {
                return RegistrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                RegistrationNumber = registrationNumber;
            }

            public String getVehicleType() {
                return vehicleType;
            }

            public void setVehicleType(String vehicleType) {
                this.vehicleType = vehicleType;
            }

            public Integer getNoOfSeats() {
                return noOfSeats;
            }

            public void setNoOfSeats(Integer noOfSeats) {
                this.noOfSeats = noOfSeats;
            }

            public String getServiceNumber() {
                return serviceNumber;
            }

            public void setServiceNumber(String serviceNumber) {
                this.serviceNumber = serviceNumber;
            }

            public List<Route> getRoutes() {
                return routes;
            }

            public void setRoutes(List<Route> routes) {
                this.routes = routes;
            }
        }
    }


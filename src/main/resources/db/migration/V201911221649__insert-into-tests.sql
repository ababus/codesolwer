DELETE
FROM tests
WHERE test_id > 0;

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('1',
        '1',
        'private static final List<Privilege> ALL_PRIVILEGES = asList(Privilege.values());
            public boolean testSortUsersByAgeDescAndNameDesc() {
                final User user1 = new User(1L, "John", "Doe", 26, ALL_PRIVILEGES);
                final User user2 = new User(2L, "Greg", "Smith", 30, ALL_PRIVILEGES);
                final User user3 = new User(3L, "Alex", "Smith", 13, ALL_PRIVILEGES);

                final List<User> sortedUsers =
                        sortByAgeDescAndNameAsc(asList(user1, user2, user3));

                final List<User> testSortedUsers = new ArrayList<>();
                testSortedUsers.add(user2);
                testSortedUsers.add(user1);
                testSortedUsers.add(user3);

                return sortedUsers.equals(testSortedUsers);
            }');


INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('2',
        '2',
        ' public boolean testReturnDistinctPrivilegesForUsers() {
                final User createUser = new User(1L, "John", "Doe", 26, asList(Privilege.CREATE, Privilege.READ));
                final User updateUser = new User(2L, "Greg", "Smith", 30, singletonList(Privilege.UPDATE));
                final User deleteUser = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));
                final User deleteUser2 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final List<Privilege> distinctPrivileges =
                        getAllDistinctPrivileges(asList(createUser, updateUser, deleteUser, deleteUser2));

                return distinctPrivileges.containsAll(asList(Privilege.CREATE, Privilege.UPDATE, Privilege.DELETE, Privilege.READ));
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('3',
        '3',
        ' public boolean testReturnUpdateUserWithAgeHigherThanGiven() {
                final User updateUser1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User updateUser2 = new User(2L, "Greg", "Smith", 30, singletonList(Privilege.UPDATE));
                final User deleteUser = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final Optional<User> foundUser =
                        getUpdateUserWithAgeHigherThan(asList(updateUser1, updateUser2, deleteUser), 29);

                return foundUser.orElse(deleteUser).equals(updateUser2);
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('4',
        '4',
        'private static final List<Privilege> ALL_PRIVILEGES = asList(Privilege.values());
        public boolean testReturnGroupedMapByNumberOfPrivileges() {
                final int ONE_PRIVILEGE = 1;
                final int TWO_PRIVILEGES = 2;
                final int FOUR_PRIVILEGES = 4;

                final User userWith2Privileges = new User(1L, "John", "Doe", 26, asList(Privilege.UPDATE, Privilege.CREATE));
                final User userWith4Privileges = new User(2L, "Greg", "Smith", 30, ALL_PRIVILEGES);
                final User userWith1Privileges1 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));
                final User userWith1Privileges2 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final Map<Integer, List<User>> groupedMap =
                        groupByCountOfPrivileges(asList(userWith2Privileges, userWith4Privileges, userWith1Privileges1, userWith1Privileges1));

                final Map<Integer, List<User>> testMap = new HashMap<>();
                testMap.put(ONE_PRIVILEGE, asList(userWith1Privileges1, userWith1Privileges2));
                testMap.put(TWO_PRIVILEGES, singletonList(userWith2Privileges));
                testMap.put(FOUR_PRIVILEGES, singletonList(userWith4Privileges));

                return groupedMap.equals(testMap);
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('5',
        '5',
        'public boolean testReturnAverageAgeForUsers() {
                final int expectedAverage = 23;

                final User user1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", "Smith", 30, singletonList(Privilege.UPDATE));
                final User user3 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final double averageAge = getAverageAgeForUsers(asList(user1, user2, user3));

                return averageAge == expectedAverage;
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('6',
        '5',
        'public boolean testReturnMinusOneInsteadOfAverageForEmptyList() {
                final int expectedAverage = -1;
                final double averageAge = getAverageAgeForUsers(emptyList());

                return averageAge == expectedAverage;
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('7',
        '6',
        'public boolean testReturnEmptyOptional(){
                final Optional<String> mostFrequentLastName2 =
                        getMostFrequentLastName(new ArrayList<User>());

                return !mostFrequentLastName2.isPresent();
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('8',
        '6',
        'public boolean testReturnMostFrequentLastName() {
                final String name = "Smith";

                final User user1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", name, 30, singletonList(Privilege.UPDATE));
                final User user3 = new User(3L, "Alex", name, 13, singletonList(Privilege.DELETE));

                final Optional<String> mostFrequentLastName =
                        getMostFrequentLastName(asList(user1, user2, user3));

                return mostFrequentLastName.orElse("").equals(name);
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('9',
        '6',
        '//this test was written by Alexandr Ter
            public boolean testReturnOptionalEmptyIfThereAreDistinctNumberOfLastNames() {
                final User user1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", "Jonson", 30, singletonList(Privilege.UPDATE));
                final User user3 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final Optional<String> mostFrequentLastName =
                        getMostFrequentLastName(asList(user1, user2, user3));

                return !mostFrequentLastName.isPresent();
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('10',
        '7',
        'public boolean testFilterListOfUsersByGivenConditions() {
                final User user1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", "Jonson", 30, asList(Privilege.UPDATE, Privilege.CREATE, Privilege.DELETE));
                final User user3 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final Predicate<User> firstNameLongerThan3 = u -> u.getFirstName().length() > 3;
                final Predicate<User> numberOfPrivilegesBiggerThan2 = u -> u.getPrivileges().size() > 2;

                final List<User> filteredUsers =
                       filterBy(asList(user1, user2, user3), firstNameLongerThan3, numberOfPrivilegesBiggerThan2);

                return filteredUsers.equals(singletonList(user2));
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('11',
        '7',
        'public boolean testReturnTheSameListIfNoPredicatesProvided() {
                final User user1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", "Jonson", 30, asList(Privilege.UPDATE, Privilege.CREATE, Privilege.DELETE));
                final User user3 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final List<User> users = asList(user1, user2, user3);

                final List<User> filteredUsers =
                       filterBy(users);

                return users.equals(filteredUsers);
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('12',
        '8',
        ' public boolean testConvertListOfUsersToCSVOfUsersLastNames() {
                final User user1 = new User(1L, "John", "Doe", 26, emptyList());
                final User user2 = new User(2L, "Greg", "Jonson", 30, emptyList());
                final User user3 = new User(3L, "Alex", "Smith", 33, emptyList());

                final String csv = convertTo(asList(user1, user2, user3), "|", User::getLastName);

                return csv.equals("Doe|Jonson|Smith");

            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('13',
        '9',
        'public boolean testReturnGroupedMapByPrivileges() {
                final User user1 = new User(1L, "John", "Doe", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", "Jonson", 30, asList(Privilege.UPDATE, Privilege.CREATE, Privilege.DELETE));
                final User user3 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final Map<Privilege, List<User>> testMap = new HashMap<>();
                testMap.put(Privilege.UPDATE, asList(user1, user2));
                testMap.put(Privilege.CREATE, singletonList(user2));
                testMap.put(Privilege.DELETE, asList(user2, user3));
                testMap.put(Privilege.READ, new ArrayList<>());

                final Map<Privilege, List<User>> groupedMap = groupByPrivileges(asList(user1, user2, user3));

                return testMap.equals(groupedMap);
            }');

INSERT INTO tests
(test_id,
 task_id,
 test_file)
VALUES ('14',
        '10',
        'public boolean testReturnGroupedMapOfLastNamesAndEncounteredNumber() {
                final User user1 = new User(1L, "John", "Jonson", 26, singletonList(Privilege.UPDATE));
                final User user2 = new User(2L, "Greg", "Smith", 30, asList(Privilege.UPDATE, Privilege.CREATE, Privilege.DELETE));
                final User user3 = new User(3L, "Alex", "Smith", 13, singletonList(Privilege.DELETE));

                final Map<String, Long> testMap = new HashMap<>();
                testMap.put("Smith", 2L);
                testMap.put("Jonson", 1L);

                final Map<String, Long> numberOfLastNames = getNumberOfLastNames(asList(user1, user2, user3));

                return testMap.equals(numberOfLastNames);
            }');

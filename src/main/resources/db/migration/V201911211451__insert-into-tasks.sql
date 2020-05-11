INSERT INTO tasks(task_id, task_title, task_body, description)
VALUES ('1',
        'Sort by Age Desc and name Asc',
        ' List<User> sortByAgeDescAndNameAsc(List<User> users){
        }',
        'Should sort given list of user by age descending and first name ascending

            @param users list of users to sort
            @return sorted list of users');
INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('2',
        'Get All Distinct Priviliges',
        'List<Privilege> getAllDistinctPrivileges(List<User> users){
        }',
        'Should return all distinct privileges for given users

            @param users list of users
            @return distinct privileges');

INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('3',
        'Users That Have Update Priviliges',
        'Optional<User> getUpdateUserWithAgeHigherThan(List<User> users, int age){
        }',
        'Should return optional of user that has UPDATE privilege
         and age higher that given age

            @param users list of users
            @param age   conditional age
            @return optional of found user or optional empty if user wasn''t found');

INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('4',
        'Group By Number Of Priviliges',
        ' Map<Integer, List<User>> groupByCountOfPrivileges(List<User> users){
        }',
        'Should return grouped map by number of user''s privileges

            @param users list of users
            @return grouped map');

INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('5',
        'Average Age For Given Users',
        'Map<Integer, List<User>> groupByCountOfPrivileges(List<User> users)){
        }',
        'Calculates average age for given users

            @param users list of user
            @return return average age for users or -1 if empty list is passed');


INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('6',
        'Most frequent last name',
        'double getAverageAgeForUsers(List<User> users){
        }',
        'Calculates average age for given users

            @param users list of user
            @return optional of most frequent last name (if it encountered at least two times)
             or optional empty if number of last names is the same or list of users is empty');

INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('7',
        'Filter List Of Users',
        ' List<User> filterBy(List<User> users, Predicate<User>... predicates){
        }',
        'Filters list of given users by given criteria

            @param users      list of users to filter
            @param predicates predicates for filtering
            @return filtered list or the same list of users if not predicates were passed');


INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('8',
        'Convert List Of Users To A String',
        ' List<User> filterBy(List<User> users, Predicate<User>... predicates){
        }',
        'Should convert list of users to comma separated values string separated by given delimiter,
             values are calculated from given map function.

             @param users     list of users to convert
             @param delimiter csv delimiter
             @param mapFun    csv value extractor
             @return csv string');

INSERT INTO tasks (task_id, task_title, task_body, description)
VALUES ('9',
        'Group Users by Priviliges',
        ' Map<Privilege, List<User>> groupByPrivileges(List<User> users){
        }',
        'Should return grouped map by user privileges

            @param users list of users to group
            @return map of privileges and users that has that privilege');

INSERT INTO tasks(task_id, task_title, task_body, description)
VALUES ('10',
        'Get Number of Last Names',
        ' Map<String, Long> getNumberOfLastNames(final List<User> users){
        }',
        'Should return map of last names and encountered number

            @param users list of users
            @return map of last names and encountered number');



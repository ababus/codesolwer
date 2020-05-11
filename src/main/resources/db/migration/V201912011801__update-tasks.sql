UPDATE tasks
SET
    task_body =
        ' List<User> sortByAgeDescAndNameAsc(List<User> users){
        return new ArrayList<User>();
        }'
WHERE task_id = '1';

UPDATE tasks
SET
    task_body =
        ' List<Privilege> getAllDistinctPrivileges(List<User> users){
        return new ArrayList<Privilege>();
        }'
WHERE task_id = '2';

UPDATE tasks
SET
    task_body =
        ' Optional<User> getUpdateUserWithAgeHigherThan(List<User> users, int age){
        return Optional.empty();
        }'
WHERE task_id = '3';

UPDATE tasks
SET
    task_body =
        '  Map<Integer, List<User>> groupByCountOfPrivileges(List<User> users){
        return new HashMap<Integer, List<User>> ();
        }'
WHERE task_id = '4';

UPDATE tasks
SET
    task_body =
        '  public double getAverageAgeForUsers(final List<User> users){
        return 0;
        }'
WHERE task_id = '5';

UPDATE tasks
SET
    task_body =
        '  Optional<String> getMostFrequentLastName(List<User> users){
        return Optional.empty();
        }'
WHERE task_id = '6';

UPDATE tasks
SET
    task_body =
        '  List<User> filterBy(List<User> users, Predicate<User>... predicates){
        return new ArrayList<User>();
        }'
WHERE task_id = '7';

UPDATE tasks
SET
    task_body =
        '  String convertTo(List<User> users, String delimiter, Function<User, String> mapFun){
        return "";
        }'
WHERE task_id = '8';

UPDATE tasks
SET
    task_body =
        '   Map<Privilege, List<User>> groupByPrivileges(List<User> users){
        return new HashMap<Privilege, List<User>>();
        }'
WHERE task_id = '9';

UPDATE tasks
SET
    task_body =
        ' Map<String, Long> getNumberOfLastNames(final List<User> users){
        return new HashMap<String, Long>();
        }'
WHERE task_id = '10';
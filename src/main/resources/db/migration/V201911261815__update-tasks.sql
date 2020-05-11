UPDATE tasks
SET
    task_body =  'public double getAverageAgeForUsers(final List<User> users){
}'
WHERE task_id = '5';

UPDATE tasks
SET
    task_body =  'Optional<String> getMostFrequentLastName(List<User> users){
}',
    description = 'Should calculate the most frequent last name from list of given users
     @param users list of users
     @return optional of most frequent last name (if it encountered at least two times)
     or optional empty if number of last names is the same or list of users is empty'
WHERE task_id = '6';

UPDATE tasks
SET
    task_body =  'String convertTo(List<User> users, String delimiter, Function<User, String> mapFun){
}'
WHERE task_id = '8';

Application running various operations on Collections and Maps, calculate and display the average execution time of these operations (time in milliseconds).

<b>Technology stack:</b>

- Language - Java
- Architecture pattern - MVVM
- View Binding
- RxJava2 + RxAndroid
- Dagger2 
- JUnit 4
- Espresso
- Mockito

<b>Functionality: </b>

3 Collections: ArrayList, LinkedList, CopyOnWriteArrayList.

- Input: number of elements to compare.

- Output: grid with [3 collections x 7 operations = 21 cells]. Operations to perform:

  - adding in the beginning;

  - adding in the middle;

  - adding in the end;

  - search by value;

  - removing in the beginning;

  - removing in the middle;

  - removing in the end.

2 Maps: TreeMap, HashMap.

- Input: number of elements to compare.

- Output: grid with 2 maps x 3 operations = 6 cells. Operations to perform:

  - adding new;

  - search by key;

  - removing.


- Each cell of the table have a text field to display the result and progress bar. While calculation is in progress, the progressBar be shown in the table cell. 

- After the operation is completed, the result(average time of execution) shown in the appropriate cell.

- The entire table is asynchronous, each operation is performed on a separate thread.

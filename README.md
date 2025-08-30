# Nightlearn - English 
(Hebrew README attached below)

### [Figma file with the app mockup and workflow](https://www.figma.com/design/Z4dW8kEu9pPkud3SwNpyRa/Nightlearn?node-id=0-1&t=vZmn1x1Fl7pNWVlB-1)
### **System Goal:**
Assist with studying for school students, university students, or anyone interested in self-learning.  
The app presents an efficient way of learning information using “double-sided” flashcards.  
Flashcards include terms the user wants to learn and their explanations.  
Through user-defined quizzes, it becomes possible to study desired topics.

### **App Capabilities**
- Create different sets with flashcards on any topic
- Create and define quiz exams based on the user’s flashcard sets
- Track a user’s learning progress based on quiz results
- Compare a user’s progress with others in a leaderboard
- Option to import lists of terms and explanations from Google Sheets as flashcards

### **Data Storage**
The system stores the following in Firebase:
- Flashcard sets created or imported by the user
- User learning progress: percentage of correct answers in quizzes, time spent on quizzes

### **App Screens**
  
**Sign Up Screen**  
On this screen, the user must enter personal details such as username, password, and email.  
A "Sign up" button below the input fields allows the user to save their details and register.  
When clicked, if registration is successful, the user will be directed to the home screen. Otherwise, a toast message will appear explaining the reason for failure.  
The screen also includes a button to navigate to the login screen, and another button allowing the user to start using the app without registration (log in as guest).

**Login Screen**  
This screen is accessed from the sign-up screen.  
Here, the user must enter their username and password to access their personal area, which contains their saved study sets and their learning progress.

**Home Screen**  
This screen displays all the app’s main features.  
It contains a list of study sets the user is working on and progress tracking (how many days the user has been studying with the app, how many cards were learned).  
At the bottom, there is a navigation bar with buttons:  
- Button to return to home ("Home")  
- Button to view all saved study sets ("Learnsets")  
- Button to view the leaderboard ("Rating")  
- Button to access the user profile screen ("Profile")  

**Create/Edit Study Set Screen**  
This screen allows the user to create or edit a set of study flashcards.  
The user can add/remove flashcards and define a quiz for this set (set the number of cards per quiz, time allowed per question, and type of question: matching cards or typing an answer manually).

**Quiz Settings Screen**  
This screen is accessed from the create/edit set screen.  
It allows the user to define a quiz game in which they will be tested on the chosen study set.  
The user can set the number and type of questions, the time allowed per question, and how many correct answers per card are required to consider it “learned.”

**User Screen (Personal Area)**  
This screen contains the user’s personal data and allows editing it.  
It also includes a control to configure app notifications and a logout option.

**Quiz Start Screen**  
This screen shows the properties of the quiz the user is about to take (number of included questions, time per question, etc.) and offers to start the quiz.

**Question Screen**  
This screen displays a question that the user must answer within the allowed time.  
After the user’s response (successful or not), they are taken either to the next question screen or to the quiz end screen.

**Quiz End Screen**  
This screen signals the end of the quiz and displays the results achieved by the user.

**About the Project Screen**  
This screen contains current information about the app project and its author.
   
   
   

# Nightlearn - hebrew

### [קובץ Figma עם הצגת מראת האפליקציה וזרימת עבודתה](https://www.figma.com/design/Z4dW8kEu9pPkud3SwNpyRa/Nightlearn?node-id=0-1&t=vZmn1x1Fl7pNWVlB-1)
### **מטרת המערכת:**
סיועה בלימודים לתלמידי ביתי ספר, לסטודנטים של אוניברסיטאות או כל מי שמעוניין בלמידה עצמית. האפליקציה מציגה שיטת למידת מידע יעילה באמצעות קלפים "דו-צדדים". קלפים כוללים מונחים אשר ברצונו של משתמש ללמוד והסבריהם. דרך חידונים המוגדרים על-ידי המשתמש מתאפשרת למידה של נושאים הרצוים.

### **יכולות האפליקציה**
- יצירת סטים שונים עם כרטיסי פלאש בנושאים כלשהם
- יצירת והגדרת בחינות חידון המבוססות על סטי קלפים של המשתמש
- מעקב התקדמות למידה של משתמש המתבסס על תוצאותיו בבחינות
- השוואת התקדמותו של משתמש אם אחרים בטבלת דרוג 
- אפשרות ליבוא רשימת מונחים והסברים הרשומים בGoogle sheets כקלפים

### **שמירת מידע**
המערכת שומרת בFirebase מידע הבא:
- סטים של קלפים הנבנים או המיובאים על ידי משתמש
- התקדמות של משתמש בלמידה: אחוז תשובות נכונות בבחינות, זמן ההושקע לבחינות

### **מסכי האפליקציה**
  
**מסך רישום**  
במסך זה המשתמש נאלץ להכניס את פרטיו האישיים כגון: שם משתמש, סיסמה ודואר אלקטרוני. כפתור "Sign up" הנמצא מתחת השדות הזנת פרטים מאפשר למשתמש לשמור את פרטיו ובכך להירשם. בעת לחיצה על הכפתור זה, אם רישום יוצלח, המשתמש יועבר למסך הבית של האפליקציה, אחרת יופיע toast שיסביר את סיבת כשל ברישום. כמו כן המסך כולל כפתור המאפשר למשתמש לעבור למסך כניסה, וכפתור המאפרש למשתמש להתחיל להשתמש באפליקציה בלי להירשם (להיכנס כאורח).

**מסך כניסה**  
מסך אליו המשתמש מועבר ממסך הרישום. במסך זה המשתמש נאלץ להכניס את פרטי שם המשתמש והסיסמה שלו על מנת שיוכל להיכנס לאזור האישי שלו המכיל סטי קלפים ללימודים הנשמרו על ידי המשתמש והתקדמותו בלמידה בסטים האלה.

**מסך בית**  
מסך המציג כל האפשרויות של האפליקציה. המסך מכיל רשימה של סטי הלימוד שהמשתמש עובד עליהם ומעקב תהליך למידה (כמה ימים המשתמש לומד באמצעות האפליקציה, כמה קלפים הוא למד). בתחתית המסך נמצא תפריט של אפשרויות נוספות כסרגל של כפתורים. הסרגל מכיל:
-	פקד חזרה למסך הבית ("Home")
-	פקד המעבר לרשימת כל סטי לימוד השמורים ("Learnsets")
-	פקד המעבר לרשימת דרוג משתמשים  ("Rating")
-	פקד מעבר למסך המשתמש ("Profil")

**מסך יצירת\עריכת סט קלפי לימוד**   
מסך זה מאפשר למשתמש ליצור או לערוך סט קלפי לימוד. באפשרותו של המשתמש להוסיף\להסיר קלפי לימוד ולהגדיר בדיקת חידון המבוצע בסט הזה (לקבוע כמות הקלפים לחידון אחד, זמן הנתון לפתרון שאלה אחד וסוג של בעיה שצריך לפתור: מציאת ההתאמה של הקלפים או הקלדת תשובה באופן ידני.

**מסך הגדרות החידון**  
מסך אליו המשתמש מועבר ממסך יצירת\עריכת סט קלפי לימוד. המסך מאפשר למשתמש להגדיר משחק חידון על ידיו הוא ייבחן בנושא של סט לימוד כרצונו. באפשרותו של המשתמש להגדיר מספר שעלות בחידון וסוגם, זמן העובר על שעולה אחת ומספר תשובות נכונות על שאלה של קלף אחד כדי לחשב שהקלף הוא נלמד.

**מסך המשתמש (אזור אישי)**  
מסך זה מכיל נתונים של המשתמש ומאפשר לשנות אותם. כמו כן המסך מכיל פקד המעביר להגדרות של הודעות האפליקציה ופקד המנתק את המשתמש מהמערכת.

**מסך תחילת החידון**  
מסך זה מציג מאפייני החידון המשתמש עומד לעבור בו (מספר שאילות הנכללות, זמן לשאלה אחד וכ"ו), ומציע להתחיל משחק עצמו.

**מסך שאלה** 
מסך זה מציג שאלה שעל המשתמש להספיק לענות עליה. אחרי תגובתו של משתמש (מוצלחת או לא) הוא מועבר למסך שאלה הבא או למסך סיום משחק.

**מסך סיום החידון**  
מסך זה מסריז על סיום המשחק ומציג תוצאותיו המשתמש השיג.

**מסך אודות הפרויקט**  
מסך זה כולל מידע אקטואלי על פרויקט האפליקציה והמחברו.



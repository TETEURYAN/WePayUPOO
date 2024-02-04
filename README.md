# We Pay U | P2
A payment systems project with an emphasis on the Object Orientation paradigm, simple information system to manage payments to employees of a company. This subject is from the Computer Science course at the Federal University of Alagoas. The course will be taught by professor Mário Hozano.
<p align="center">
  <img src="https://user-images.githubusercontent.com/91018438/204195385-acc6fcd4-05a7-4f25-87d1-cb7d5cc5c852.png" alt="animated" />
</p>

<center>
Group:

  
    Matheus Ryan | Hugo Coelho
 </center>

 ## About the project

 The purpose of the project is to build a payroll system. The system consists of a database of a company's employees in addition to their associated data such as time cards. The system must pay each employee. Employees must receive the right salary, at the right time, using the method they prefer. What's more, various fees and taxes are deducted from your salary.

* Some employees are hourly. They receive a salary for each hour worked. They submit "time cards" every day to report the number of hours worked that day. If an employee works more than 8 hours, they must be paid 1.5 the normal rate during overtime. They get paid every Friday.
* Some employees receive a fixed monthly salary. They are paid on the last business day of the month (ignore holidays). Such employees are called "salaried employees".
* Some salaried employees are commissioned and therefore receive a commission, a percentage of the sales they make. They submit sales results that inform the date and value of the sale. The commission percentage varies from employee to employee. They are paid every 2 Fridays; At this time, they must receive the equivalent of 2 weeks' fixed salary plus commissions for the period.
     
Employees can choose the payment method.
  * You can receive a check in the mail
  * You can receive a check in hand
  * You can request a deposit into a bank account
    
Some employees belong to the union (to put it simply, there is only one possible union). The union charges a monthly fee to the employee and this fee may vary between employees. The union fee is deducted from the salary. Furthermore, the union may occasionally charge additional service fees to an employee. Such service fees are submitted by the union on a monthly basis and must be deducted from the employee's next paycheck. Employee identification in the union is not the same as identification in the payroll system.The payroll is run every day and must pay employees whose salaries are due that day. The system will receive the date by which payment must be made and will calculate the payment for each employee since the last time they were paid.
 

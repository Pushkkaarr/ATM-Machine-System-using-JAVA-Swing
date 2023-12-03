package atm_app;
import javax.swing.*;
import java.awt.*;
import static java.awt.Component.TOP_ALIGNMENT;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankAccount extends javax.swing.JFrame implements ActionListener {

    private JButton depositButton;
    private JButton withdrawButton;
    private JButton balanceButton;
    private JButton changePinButton;
    private JButton cancel;
    private JLabel balanceLabel;
    private JLabel status;
    private int pin;
    private JPanel panel;
    private JFrame f1;
    private String cardnumber;
    String firstname; 
    String middlename; 
    String lastname; 
    int newpin;
    Double money;
 
    public BankAccount(String cardnumber) {
        this.cardnumber=cardnumber;
        setTitle("Bank Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(BankAccount.MAXIMIZED_BOTH);
   setLocationRelativeTo(null);
   f1=new JFrame();
   panel = new JPanel();
   f1.setExtendedState(MAXIMIZED_BOTH);
   f1.add(panel);
      
        panel.setLayout(new GridLayout(4, 2));    
        panel.setAlignmentX(TOP_ALIGNMENT);
        panel.setAlignmentY(TOP_ALIGNMENT);
                 
        status=new JLabel("Transaction Status : OK");
        panel.add(status);
        status.setFont(new Font("Courier New", Font.BOLD, 20));
        status.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
         try{
             Statement statement = Conn.getStatement();
        String query1 = "SELECT * FROM users WHERE cardnumber='" + cardnumber + "'";
        ResultSet rs = statement.executeQuery(query1);
         if(rs.next()){
             money=rs.getDouble("balance");
         }}
         catch(SQLException e){
             e.printStackTrace();
         }
        balanceLabel = new JLabel("Your Balance is: ₹"+money);
        panel.add(balanceLabel);
        balanceLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        balanceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
         depositButton = new JButton("Deposit");
         depositButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
              depositButtonActionPerformed(evt);
            }
        });
        panel.add(depositButton);
        depositButton.setFont(new Font("Courier New", Font.BOLD, 26));

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
         withdrawButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
               withdrawButtonActionPerformed(evt);
            }});
        panel.add(withdrawButton);
        withdrawButton.setFont(new Font("Courier New", Font.BOLD, 26));

        balanceButton = new JButton("Bank Details");
        balanceButton.addActionListener(this);
        balanceButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
               balanceButtonActionPerformed(evt);
            }});
        panel.add(balanceButton);
        balanceButton.setFont(new Font("Courier New", Font.BOLD, 26));

        changePinButton = new JButton("Change PIN");
        changePinButton.addActionListener(this);
         changePinButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
               changePinButtonActionPerformed(evt);
            }});
        panel.add(changePinButton);
        changePinButton.setFont(new Font("Courier New", Font.BOLD, 26));
        
        cancel = new JButton("EXIT");
        cancel.addActionListener(this);
         cancel.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
               cancelActionPerformed(evt);
            }});
        panel.add(cancel);
        cancel.setFont(new Font("Courier New", Font.BOLD, 26));
       
    setContentPane(panel);
 
    }
    //1st deposit action
 private void depositButtonActionPerformed(ActionEvent evt) {
      if(evt.getSource()==depositButton){
            String deposit = JOptionPane.showInputDialog(null, "Enter deposit amount: ");
            if(deposit.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Error! You need to enter an amount to Deposit");
                return;
            }
            else  if(Double.parseDouble(deposit)>25000){
            status.setText("Transaction Status : Exceeding the Limit to Deposit Money");
            return;}
            else {
            
            money += Double.parseDouble(deposit);
            balanceLabel.setText("Your Balance is: ₹" + String.format("%.2f", money));}
            status.setText("Transaction Status : Deposit Completed of "+deposit+"₹");
             try {
      
            Statement statement = Conn.getStatement();
             
                String query1 = "UPDATE users SET balance = " + money + " WHERE cardnumber = '" + cardnumber+ "'";
          statement.executeUpdate(query1);
        }
              catch (SQLException e) {
               e.printStackTrace();
}
        }
              
     }
     //2nd withdraw action
  private void withdrawButtonActionPerformed(ActionEvent evt) {
              if(evt.getSource()==withdrawButton){
            String withdraw = JOptionPane.showInputDialog(null, "Enter withdraw amount: ");
            if(withdraw.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Error! You need to enter an amount to withdraw");
                return;
            }
            else if(Double.parseDouble(withdraw)>25000){
            status.setText("Transaction Status : Cannot Withdraw such Large Amount");
            return;}
            else if(Double.parseDouble(withdraw)>money){
                 JOptionPane.showMessageDialog(null, "Insufficient funds!");
            }
            else {
                status.setText("Transaction Status : Withdrawal Completed of "+withdraw+"₹");
            money -= Double.parseDouble(withdraw);
            balanceLabel.setText("Your Balance is: ₹" + String.format("%.2f", money));}
             try {
            Statement statement = Conn.getStatement();
            
                String query1 = "UPDATE users SET balance = " + money + " WHERE cardnumber = '" + cardnumber+ "'";
          statement.executeUpdate(query1);
        }
              catch (SQLException e) {
               e.printStackTrace();
}
        }
        }
      //3rd balance info  action
  private void balanceButtonActionPerformed(ActionEvent evt) {
    if (evt.getSource() == balanceButton) {
        status=new JLabel("Transaction Status : OK");
    try {
        Statement statement = Conn.getStatement();
        String query = "SELECT * FROM users WHERE cardnumber='" + cardnumber + "'";
        ResultSet rs = statement.executeQuery(query);
        
        if (rs.next()) {
            String cardnumberValue = rs.getString("cardnumber");
            String firstnameValue = rs.getString("firstname");
            String middlenameValue = rs.getString("middlename");
            String lastnameValue = rs.getString("lastname");
            String genderValue=rs.getString("gender");
            String Age=rs.getString("age");
            String DOB=rs.getString("dob");
            String info="Information of Card Holder";
            String message = "<html><font face='century gothic' size='6'>Card number :  " + cardnumberValue + "\n\n" +
                             "<html><font face='century gothic' size='6'>First name :   " + firstnameValue + "\n\n" +
                             "<html><font face='century gothic' size='6'>Middle name :  " + middlenameValue + "\n\n" +
                             "<html><font face='century gothic' size='6'>Last name :    " + lastnameValue+"\n\n"+
                             "<html><font face='century gothic' size='6'>Age :          " +Age+"\n\n"+
                             "<html><font face='century gothic' size='6'>Gender :       "+genderValue+"\n\n"+
                             "<html><font face='century gothic' size='6'>Date of Birth : "+DOB+"</font></html>";
      
            JOptionPane.showMessageDialog(null, message, "Information of Card Holder", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Card number not found.");
        }
        
        statement.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        e.printStackTrace();
    }
}}
      //4th changepin action
     private void changePinButtonActionPerformed(ActionEvent evt) {
                if (evt.getSource() == changePinButton){
                    status=new JLabel("Transaction Status : OK");
    try{
        String currentPin = JOptionPane.showInputDialog(null, "Enter your current PIN: ");
        Statement statement = Conn.getStatement();
        
        String query = "SELECT * FROM users WHERE pin='" + currentPin + "'";
        ResultSet rs = statement.executeQuery(query);
        
         if (rs.next()) {
            int currentPinValue = rs.getInt("pin");
            
            if (currentPinValue == Integer.parseInt(currentPin)) {
                String newPin = JOptionPane.showInputDialog(null, "Enter your new PIN: ");
                int newPinValue = Integer.parseInt(newPin);
                if (newPin.length() != 4) {
                     JOptionPane.showMessageDialog(null, "PIN should be exactly 4 digits.");
                       return; }

                String updateQuery = "UPDATE users SET pin='" + newPinValue + "' WHERE cardnumber = '" + cardnumber+ "'";
                statement.executeUpdate(updateQuery);
                
                JOptionPane.showMessageDialog(null, "PIN changed successfully!");
                } 
       
    }     else{
    JOptionPane.showMessageDialog(null, "Current PIN is incorrect!");
    return;
}
    }
    catch(SQLException e){
        e.printStackTrace();
    }
}
   }   
         //5th exit action
   private void cancelActionPerformed(ActionEvent evt){
       if(evt.getSource()==cancel){
           this.dispose();
             main2 m4=new main2("");
             m4.setVisible(true);
           
       }
   }
    
    public void actionPerformed(ActionEvent event) {
// Gives error if not included actionperformed event hence this.
    }

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount("");
        bankAccount.setVisible(true);
        
    }
}
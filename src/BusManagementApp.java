import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BusManagementApp extends JFrame {
    private List<Bus> buses = new ArrayList<>();
    private JLabel driverNameLabel;
    private JTextField driverNameField;
    private JLabel busNumberLabel;
    private JTextField busNumberField;
    private JLabel routeNumberLabel;
    private JTextField routeNumberField;
    private JLabel brandLabel;
    private JTextField brandField;
    private JLabel yearOfManufactureLabel;
    private JTextField yearOfManufactureField;
    private JLabel mileageLabel;
    private JTextField mileageField;
    private JLabel questRouteLabel;
    private JTextField questRouteField;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton showAllButton;
    private JButton questRouteButton;
    private JButton questYearButton;
    private JButton questMileageButton;
    

    private JList<Bus> busList;
    private DefaultListModel<Bus> busListModel;

    private BusDatabaseManager databaseManager;

    public BusManagementApp() {
        setTitle("Управление автобусами");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        databaseManager = new BusDatabaseManager();

        driverNameLabel = new JLabel("Фамилия и инициалы водителя:");
        driverNameField = new JTextField(20);
        busNumberLabel = new JLabel("Номер автобуса:");
        busNumberField = new JTextField(20);
        routeNumberLabel = new JLabel("Номер маршрута:");
        routeNumberField = new JTextField(20);
        brandLabel = new JLabel("Марка:");
        brandField = new JTextField(20);
        yearOfManufactureLabel = new JLabel("Год начала эксплуатации:");
        yearOfManufactureField = new JTextField(20);
        mileageLabel = new JLabel("Пробег:");
        mileageField = new JTextField(20);
	questRouteLabel = new JLabel("Пробег для поиска:");
        questRouteField = new JTextField(20);

        addButton = new JButton("Добавить");
        updateButton = new JButton("Обновить");
        deleteButton = new JButton("Удалить");
	showAllButton = new JButton("Показать все");
	questRouteButton = new JButton("Найти по номеру маршрута");
	questYearButton = new JButton("Эксплуатация больше 10 лет");
	questMileageButton = new JButton("Пробег больше 100000 км");

        busListModel = new DefaultListModel<>();
        busList = new JList<>(busListModel);

        JPanel inputPanel = new JPanel(new GridLayout(4, 4));
        inputPanel.add(driverNameLabel);
        inputPanel.add(driverNameField);
        inputPanel.add(busNumberLabel);
        inputPanel.add(busNumberField);
        inputPanel.add(routeNumberLabel);
        inputPanel.add(routeNumberField);
        inputPanel.add(brandLabel);
        inputPanel.add(brandField);
        inputPanel.add(yearOfManufactureLabel);
        inputPanel.add(yearOfManufactureField);
        inputPanel.add(mileageLabel);
        inputPanel.add(mileageField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(showAllButton);
	
	JPanel questInputPanel = new JPanel(new GridLayout(2, 3));
	questInputPanel.add(questRouteLabel);
	questInputPanel.add(questRouteField);
	questInputPanel.add(questRouteButton);
	questInputPanel.add(questYearButton);
	questInputPanel.add(questMileageButton);
	//questInputPanel.add(showAllButton);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Список автобусов:"), BorderLayout.NORTH);
        listPanel.add(new JScrollPane(busList), BorderLayout.CENTER);
	
	JPanel main = new JPanel(new GridLayout(1, 2));
	JPanel mainIN = new JPanel(new GridLayout(2, 1));
        mainIN.add(inputPanel);
	mainIN.add(questInputPanel);
        main.add(mainIN);
        main.add(listPanel);
	add(main);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBus();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBus();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBus();
            }
        });
	
	showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBusList();
            }
        });
	
	questRouteButton.addActionListener(new ActionListener(){
	    @Override
            public void actionPerformed(ActionEvent e) {
		int route = Integer.parseInt(questRouteField.getText());
		busListModel.clear();
		List<Bus> buses = databaseManager.getQuestRouteBuses(route);
		for (Bus bus : buses) {
		    busListModel.addElement(bus);
		}
            }
	});
	
	questYearButton.addActionListener(new ActionListener(){
	    @Override
            public void actionPerformed(ActionEvent e) {
		busListModel.clear();
		List<Bus> buses = databaseManager.getQuestYearsBuses();
		for (Bus bus : buses) {
		    busListModel.addElement(bus);
		}
            }
	});
	
	questMileageButton.addActionListener(new ActionListener(){
	    @Override
            public void actionPerformed(ActionEvent e) {
		busListModel.clear();
		List<Bus> buses = databaseManager.getQuestMileageBuses();
		for (Bus bus : buses) {
		    busListModel.addElement(bus);
		}
            }
	});

        busList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Bus selectedBus = busList.getSelectedValue();
                if (selectedBus != null) {
                    displayBus(selectedBus);
                }
            }
        });

        updateBusList();
    }

    private void addBus() {
        String driverName = driverNameField.getText();
        String busNumber = busNumberField.getText();
        String routeNumber = routeNumberField.getText();
        String brand = brandField.getText();
        int yearOfManufacture = Integer.parseInt(yearOfManufactureField.getText());
        int mileage = Integer.parseInt(mileageField.getText());

        Bus bus = new Bus(driverName, busNumber, routeNumber, brand, yearOfManufacture, mileage);
        databaseManager.addBus(bus);
        updateBusList();
        clearFields();
    }

    private void updateBus() {
        Bus selectedBus = busList.getSelectedValue();
        if (selectedBus == null) {
            JOptionPane.showMessageDialog(this, "Выберите автобус для обновления");
            return;
        }

        String driverName = driverNameField.getText();
        String busNumber = busNumberField.getText();
        String routeNumber = routeNumberField.getText();
        String brand = brandField.getText();
        int yearOfManufacture = Integer.parseInt(yearOfManufactureField.getText());
        int mileage = Integer.parseInt(mileageField.getText());

        selectedBus.setDriverName(driverName);
        selectedBus.setBusNumber(busNumber);
        selectedBus.setRouteNumber(routeNumber);
        selectedBus.setBrand(brand);
        selectedBus.setYearOfManufacture(yearOfManufacture);
        selectedBus.setMileage(mileage);

        databaseManager.updateBus(selectedBus);
        updateBusList();
        clearFields();
    }

    private void deleteBus() {
        Bus selectedBus = busList.getSelectedValue();
        if (selectedBus == null) {
            JOptionPane.showMessageDialog(this, "Выберите автобус для удаления");
            return;
        }

        databaseManager.deleteBus(selectedBus);
        updateBusList();
        clearFields();
    }

    private void updateBusList() {
        busListModel.clear();
        List<Bus> buses = databaseManager.getAllBuses();
        for (Bus bus : buses) {
            busListModel.addElement(bus);
        }
    }

    private void displayBus(Bus bus) {
        driverNameField.setText(bus.getDriverName());
        busNumberField.setText(bus.getBusNumber());
        routeNumberField.setText(bus.getRouteNumber());
        brandField.setText(bus.getBrand());
        yearOfManufactureField.setText(String.valueOf(bus.getYearOfManufacture()));
        mileageField.setText(String.valueOf(bus.getMileage()));
    }

    private void clearFields() {
        driverNameField.setText("");
        busNumberField.setText("");
        routeNumberField.setText("");
        brandField.setText("");
        yearOfManufactureField.setText("");
        mileageField.setText("");
        busList.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BusManagementApp app = new BusManagementApp();
            app.setVisible(true);
        });
    }
}

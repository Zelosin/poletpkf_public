package zelosin.pack.controllers.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuController {

    public class MenuNavigation{
        private String index;
        private String domSection;
        public MenuNavigation(String index, String domSection) {
            this.index = index;
            this.domSection = domSection;
        }
        public String getIndex() {
            return index;
        }
        public String getDomSection() {
            return domSection;
        }
    }

    private ObservableList<MenuNavigation> mMenuNavigationCollection = FXCollections.observableArrayList();

    @FXML
    private TableView dMenuTable;
    @FXML
    private TableColumn<MenuNavigation, String> dMenuTableSection;
    @FXML
    private TableColumn<MenuNavigation, String> dMenuTableButton;
    public void initialize(){
        dMenuTableSection.setCellValueFactory(new PropertyValueFactory<MenuNavigation, String>("domSection"));
        dMenuTableButton.setCellValueFactory(new PropertyValueFactory<MenuNavigation, String>("index"));
        dMenuTable.setItems(mMenuNavigationCollection);
        mMenuNavigationCollection.add(new MenuNavigation("1","СОСТОЯНИЕ ТЕПЛОВОЗА"));
        mMenuNavigationCollection.add(new MenuNavigation("2","РАСШИРЕННАЯ ДИАГНОСТИКА ТЕПЛОВОЗА"));
        mMenuNavigationCollection.add(new MenuNavigation("3","НАСТРОЙКА ДАТЫ И ВРЕМЕНИ"));
        mMenuNavigationCollection.add(new MenuNavigation("4","КОПИРОВАНИЕ ДАННЫХ РЕГИСТРАЦИИ"));
        mMenuNavigationCollection.add(new MenuNavigation("5","ВЕРСИЯ ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ"));
        mMenuNavigationCollection.add(new MenuNavigation("6","ЗАВЕРШЕНИЕ РАБОТЫ ДИСПЛЕЙНОГО МОДУЛЯ"));
        mMenuNavigationCollection.add(new MenuNavigation("0","ВЫХОД В ГЛАВНОЕ МЕНЮ"));
    }
}

















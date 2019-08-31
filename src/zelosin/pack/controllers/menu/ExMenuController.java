package zelosin.pack.controllers.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExMenuController {

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
    private ObservableList<ExMenuController.MenuNavigation> mMenuNavigationCollection = FXCollections.observableArrayList();

    @FXML
    private TableView dMenuTable;
    @FXML
    private TableColumn<MenuController.MenuNavigation, String> dMenuTableSection;
    @FXML
    private TableColumn<MenuController.MenuNavigation, String> dMenuTableButton;
    public void initialize(){
        dMenuTableSection.setCellValueFactory(new PropertyValueFactory<MenuController.MenuNavigation, String>("domSection"));
        dMenuTableButton.setCellValueFactory(new PropertyValueFactory<MenuController.MenuNavigation, String>("index"));
        dMenuTable.setItems(mMenuNavigationCollection);
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("1","ВСЕ ДИСКРЕТНЫЕ ПАРАМЕТРЫ ТЕПЛОВОЗА"));
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("2","ВСЕ АНАЛОГОВЫЕ ПАРАМЕТРЫ"));
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("3","СОСТОЯНИЕ ТУМБЛЕРОВ"));
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("4","СОСТОЯНИЕ ДАТЧИКОВ"));
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("5","СОСТОЯНИЕ ВНЕШНИХ БЛОКОВ"));
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("6","ПРОВЕРКА ЭЛЕКТРИЧЕСКОЙ СХЕМЫ"));
        mMenuNavigationCollection.add(new ExMenuController.MenuNavigation("0","ВЫХОД ИЗ РЕЖИМА СОСТОЯНИЯ ТЕПЛОВОЗА"));
    }
}

package topjava.grad.data;

import topjava.grad.TestMatcher;
import topjava.grad.domain.Menu;
import topjava.grad.domain.to.MenuTo;

import java.time.LocalDate;

import static topjava.grad.data.RestaurantTestData.PLACE_1_ID;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = new TestMatcher<>(Menu.class, "restaurant", "dishes");
    public static final int MENU_1_ID = 100005;
    public static final int MENU_2_ID = 100006;
    public static final int MENU_3_ID = 100007;

    public static final Menu MENU_1 = new Menu(MENU_1_ID, LocalDate.now());
    public static final Menu MENU_2 = new Menu(MENU_2_ID, LocalDate.now());
    public static final Menu MENU_3 = new Menu(MENU_3_ID, LocalDate.now().minusDays(1));

    public static MenuTo getUpdated() {
        return new MenuTo(MENU_1_ID, PLACE_1_ID,LocalDate.now().minusDays(1));
    }

    public static MenuTo getNew() {
        return new MenuTo(null, PLACE_1_ID, LocalDate.now());
    }
}

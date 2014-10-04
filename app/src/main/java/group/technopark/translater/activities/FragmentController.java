package group.technopark.translater.activities;


import android.app.Fragment;

public interface FragmentController {
    public void setFragment(int container, Fragment fragment, String tag, Boolean backStack);
}

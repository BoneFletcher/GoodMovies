package denis.frost.testnd.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import denis.frost.testnd.R;

public class ComingSoonFragment extends Fragment{
    public static final int LAYOUT = R.layout.fragment_coming_soon;
    private View view;

    public static ComingSoonFragment getInstace () {
        Bundle args = new Bundle();
        ComingSoonFragment csFragment = new ComingSoonFragment();
        csFragment.setArguments(args);
        return csFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT,container,false);
        return view;
    }
}

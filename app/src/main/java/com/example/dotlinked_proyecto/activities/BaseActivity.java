package com.example.dotlinked_proyecto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.dotlinked_proyecto.API.Class.Token;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.Utils.UtilMessages;
import com.example.dotlinked_proyecto.activities.login.AccessActivity;
import com.example.dotlinked_proyecto.claims.ClaimsFragment;
import com.example.dotlinked_proyecto.events.EventsCalendarFragment;
import com.example.dotlinked_proyecto.notify.NotifyFragment;
import com.example.dotlinked_proyecto.personal.PersonalFragment;
import com.example.dotlinked_proyecto.services.ServicesFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    DrawerLayout.DrawerListener {

  private final static String TAG_FRAGMENT = "fragment";
  private final static String TAG_AUTH = "needAuth";
  public ListView drawerList;
  protected DrawerLayout drawerLayout;
  public String[] layers;
  protected ActionBarDrawerToggle drawerToggle;
  private Map map;
  private Toolbar appbar;
  private NavigationView navView;
  private Session session;
  FragmentManager fragmentManager = getSupportFragmentManager();
  private Fragment fragment;
  private String rol;
  private String companyId;
  private Token token;
  private NavigationView navigationView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    session = new Session(this);

    Toolbar toolbar = findViewById(R.id.toolbar);

    setSupportActionBar(findViewById(R.id.toolbar));

    drawerLayout = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    rol = session.getUserRoles();
    token = session.getToken();
    companyId = session.getCompanyIdUser();

    drawerLayout.addDrawerListener(this);

    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_list).setOnClickListener(
        view -> Toast.makeText(BaseActivity.this, String.format(getString(R.string.title_click), navigationView.getTag()),
            Toast.LENGTH_SHORT).show());

    String fragmentName = getString(R.string.events);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      fragmentName = bundle.getString(TAG_FRAGMENT, getString(R.string.events));
    }
    selectFragment(rol, companyId, token, fragmentName);
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }
  private void setMenuSelectedItem(int pos) {
    MenuItem menuItem = navigationView.getMenu().getItem(pos);
    menuItem.setChecked(true);
  }

  private void selectFragment(String rol, String companyId, Token token, String fragmentName) {
    if (fragmentName.equals(getString(R.string.events))) {
      fragment = EventsCalendarFragment.newInstance(rol, companyId, token);
      setTitle(R.string.events);
      setMenuSelectedItem(0);
    } else if (fragmentName.equals(getString(R.string.notify))) {
      fragment = NotifyFragment.newInstance("", "");
      setTitle(R.string.notify);
      setMenuSelectedItem(1);
    } else if (fragmentName.equals(getString(R.string.claim))) {
      fragment = ClaimsFragment.newInstance(token);
      setTitle(R.string.claim);
      setMenuSelectedItem(2);
    } else if (fragmentName.equals(getString(R.string.services))) {
      fragment = ServicesFragment.newInstance(rol, companyId, token);
      setTitle(R.string.services);
      setMenuSelectedItem(3);
    } else if (fragmentName.equals(getString(R.string.personal))) {
      fragment = PersonalFragment.newInstance(companyId, token);
      setTitle(R.string.personal);

      setMenuSelectedItem(4);
    }
    fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      List<Fragment> fragments = getSupportFragmentManager().getFragments();
      Fragment currentFragment = fragments.get(0);
      if (currentFragment instanceof EventsCalendarFragment) {
        Intent intent = new Intent(this, AccessActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // TODO comprobar expiración de token
        intent.putExtra(TAG_AUTH, false);
        startActivity(intent);
      } else if (currentFragment instanceof ClaimsFragment ||
          currentFragment instanceof PersonalFragment ||
          currentFragment instanceof ServicesFragment) {
        startActivityFragment(getString(R.string.events));
      }
      dismissCurrentFragment(currentFragment);
    }
  }

  private void dismissCurrentFragment(Fragment currentFragment) {
    Objects.requireNonNull(currentFragment.getActivity()).finish();
  }

  private void startActivityFragment(String extras) {
    Intent intent = new Intent(this, BaseActivity.class);
    intent.putExtra(TAG_FRAGMENT, extras);
    startActivity(intent);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.home) {
      Intent intent = new Intent(this, AccessActivity.class)
              .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      intent.putExtra(TAG_AUTH, false);
      startActivity(intent);
    }

    return super.onOptionsItemSelected(item);

  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    Fragment currentFragment = fragments.get(0);
    if (menuItem.getItemId() != R.id.exit)
      dismissCurrentFragment(currentFragment);
    switch (menuItem.getItemId()) {
      case R.id.events:
        startActivityFragment(getString(R.string.events));
        break;
      case R.id.notify:
        startActivityFragment(getString(R.string.notify));
        break;
      case R.id.claim:
        startActivityFragment(getString(R.string.claim));
        break;
      case R.id.services:
        startActivityFragment(getString(R.string.services));
        break;
      case R.id.personal:
        startActivityFragment(getString(R.string.personal));
        break;
      case R.id.exit:
        UtilMessages.showExitMessage(this);
        break;
      default:
        throw new IllegalArgumentException(getString(R.string.load_data_err));
    }
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }

  @Override
  public void onDrawerSlide(@NonNull View view, float v) {
    //cambio en la posición del drawer
  }

  @Override
  public void onDrawerOpened(@NonNull View view) {
    //el drawer se ha abierto completamente
    Toast.makeText(this, getString(R.string.navigation_drawer_open),
        Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDrawerClosed(@NonNull View view) {
    //el drawer se ha cerrado completamente
  }

  @Override
  public void onDrawerStateChanged(int i) {
    //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
  }

  public void onClickListener(View view) {
    Toast.makeText(this, "You clicked " + view.getId(), Toast.LENGTH_SHORT).show();
  }


}

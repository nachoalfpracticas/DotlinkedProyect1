package com.example.dotlinked_proyecto.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.dotlinked_proyecto.Claims.ClaimsFragment;
import com.example.dotlinked_proyecto.Persistence.Session;
import com.example.dotlinked_proyecto.R;
import com.example.dotlinked_proyecto.activities.login.AccessActivity;
import com.example.dotlinked_proyecto.classEvent.EventsCalendarFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Map;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
    DrawerLayout.DrawerListener {

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

    NavigationView navigationView = findViewById(R.id.navigation_view);
    navigationView.setNavigationItemSelectedListener(this);

    rol = session.getUserRoles();
    token = session.getToken();
    companyId = session.getCompanyIdUser();
    MenuItem menuItem = navigationView.getMenu().getItem(0);
    //onNavigationItemSelected(menuItem);
    menuItem.setChecked(true);

    drawerLayout.addDrawerListener(this);

    View header = navigationView.getHeaderView(0);
    header.findViewById(R.id.header_list).setOnClickListener(view -> Toast.makeText(BaseActivity.this, String.format(getString(R.string.title_click), navigationView.getTag()),
        Toast.LENGTH_SHORT).show());

    selectDefaultView(rol, companyId, token);
  }

  private void selectDefaultView(String rol, String companyId, Token token) {
    fragment = EventsCalendarFragment.newInstance(rol, companyId, token);
    fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();
  }

  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      Intent intent = new Intent(this, AccessActivity.class);
      intent.putExtra("needAuth", false);
      startActivity(intent);
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    int title;
    switch (menuItem.getItemId()) {
      case R.id.events:
        title = R.string.events;
        finish();
        startActivity(getIntent());
        break;
      case R.id.notify:
        title = R.string.notify;
        break;
      case R.id.claim:
        title = R.string.claim;
        fragment = ClaimsFragment.newInstance(token);
        fragmentManager.beginTransaction().replace(R.id.home_content, fragment).commit();
        break;
      case R.id.health:
        title = R.string.health;
        break;
      case R.id.date:
        title = R.string.dates;
        break;
      case R.id.personal:
        title = R.string.personal;
        break;
      default:
        throw new IllegalArgumentException("menu option not implemented!!");
    }


    setTitle(getString(title));

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

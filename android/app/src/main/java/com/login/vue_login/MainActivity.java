package com.login.vue_login;

import com.getcapacitor.BridgeActivity;
import android.os.Bundle;
import com.getcapacitor.Plugin;
 
import java.util.ArrayList;
 
public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 
    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
        // Additional plugins you've installed go here
        // add(com.getcapacitor.community.facebooklogin.FacebookLogin.class);
        add(jp.rdlabo.capacitor.plugin.facebook.FacebookLogin.class);
    }});
  }
}
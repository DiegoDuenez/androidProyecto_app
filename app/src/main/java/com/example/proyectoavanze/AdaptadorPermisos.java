package com.example.proyectoavanze;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorPermisos extends RecyclerView.Adapter<AdaptadorPermisos.MiHolder> {
    final private int REQUEST_CODE_ASK_PERMISSION = 111;

    private List<Permiso> permisosList;
    private Activity activity;


    public AdaptadorPermisos(List<Permiso> permisosList,Activity activity) { //SE CREA UN COSNTRUCTOR QUE RECIBE LA LISTA DEL MODELO PERSONA
        this.permisosList = permisosList;
        this.activity = activity;
    }
    //private View.OnClickListener listener;

    @NonNull
    @Override
    public AdaptadorPermisos.MiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //CON EL LAYOUTINFLATER ESTAMOS CREANDO UNA VISTA APARTIR DEL XML YA DEFINIDO QUE ES EL ITEM_LAYOUT QUE CREAMOS
        View vistaPermisos = LayoutInflater.from(parent.getContext()).inflate(R.layout.permisos_item_layout, parent, false);
        return new MiHolder(vistaPermisos);

    }

    @Override
    public void onBindViewHolder(@NonNull MiHolder holder, int position) {
        holder.setData(permisosList.get(position),activity);

    }

    @Override
    public int getItemCount() { //CONTEO DE LOS ITEMS
        return permisosList.size();
    }

    public class MiHolder extends RecyclerView.ViewHolder {


        private Switch texto;
        public String permisoTipo;
        public Activity activitypub;


        public MiHolder(@NonNull View itemView) {
            super(itemView);

            texto = itemView.findViewById(R.id.sw_permiso);
            texto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int permiso = ActivityCompat.checkSelfPermission(itemView.getContext(),permisoTipo);

                    int permisoLlamar = ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.CALL_PHONE);
                    int permisoContactos = ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.READ_CONTACTS);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(activity, new String[]{permisoTipo}, 555);
                    }



                    if(permisoContactos == PackageManager.PERMISSION_GRANTED &&
                            permisoLlamar == PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.startActivity(itemView.getContext(), new Intent(itemView.getContext(),LoginActivity.class),null);
                    }

                    return;



                    }
            });
        }

        private void setData(Permiso p, Activity activity){ //RECIBE UNA PERSONA Y LE SACA LA INFORMACION
            texto.setText(p.getNombre());
           permisoTipo= p.getPermiso();
            this.activitypub=activitypub;
        }
    }
}

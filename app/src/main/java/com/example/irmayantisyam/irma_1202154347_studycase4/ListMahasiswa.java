package com.example.irmayantisyam.irma_1202154347_studycase4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {
    //mendekalarasikan variabel yang digunakan
    Button sync;
    ListView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        //inisiasi masing-masing variabel
        sync = findViewById(R.id.synctask);
        daftar = findViewById(R.id.listmhs);
    }

    public void loading (View view){ //membuat method ketika button dipilih
        new getData(daftar).execute(); //memulai proses AsyncTask
    }

    class getData extends AsyncTask<String, Integer, String>{ //membuat sub-class AsyncTask
        //mendeklarasikan semua variabel yang digunakan
        ListView daftar;
        ArrayAdapter adapter;
        ArrayList<String> list;
        ProgressDialog progres;

        public getData(ListView daftar){ //contructor ketika AsyncTask diinisiasi
            //menginisiasi variabel yang digunakan
            this.daftar = daftar;
            progres = new ProgressDialog(ListMahasiswa.this);
            list = new ArrayList<>();
        }
        @Override
        protected void onPreExecute(){ //method yang digunakan sebelum AsyncTask dimulai
            super.onPreExecute();

            progres.setMessage("Tunggu beberapa saat"); //Pesan pada AsyncTask
            progres.setIndeterminate(false); //mengatur tampilan progress
            progres.setProgress(0); //mengatur ukuran minimal dari progres
            progres.setMax(100); //mengatur ukuran full atau maksimal progress
            //sehingga artinya, progres loading akan dimulai dari 0 hingga 100
            progres.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); //mengatur arah loading progress, yaitu horizontal (mendatar)
            progres.show(); //method untuk menampilkan progres
        }

        @Override
        protected String doInBackground(String... strings){ //method ketika AsyncTask dilakukan
            //membuat adapter
            adapter = new ArrayAdapter<>(ListMahasiswa.this, android.R.layout.simple_list_item_1, list);
            //membuat dan menyimpan array kedalam variabel mahasiswa pada strings.xml
            String [] mahasiswa = getResources().getStringArray(R.array.mahasiswa);
            //membuat pengkondisian untuk menyimpan arrah ke dalam variabel a
            for (int a=0; a<mahasiswa.length; a++){ //inisiasi kondisi
                long persen = 100L*a/mahasiswa.length; //membuat formul untuk lama persenan pernama-nya
                //mengatur ketika meload satu nama berapa persen yang akan ditampilkan, dan disesuaikan dengan progres bar
                try{
                    progres.setProgress((int)persen); //mengatur progresna
                    Thread.sleep(250); //mengatur waktu delay
                    list.add(mahasiswa[a]); //menambahkan item ke dalam variabel yang memuat arraylist
                } catch (InterruptedException e){ //ketika eksekusi gagal
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute (String b){ //method ketika AsyncTask telah dilakukan
            super.onPostExecute(b);
            daftar.setAdapter (adapter); //mengatur adapternya
            progres.dismiss();//menutup progres bar
        }
    }
}

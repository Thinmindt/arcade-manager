package com.plotsandschemes.arcade;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import layout.store;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 25;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static AvailableGamesList gamesList;
    public static final String host = "arcade.plotsandschemes.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gamesList = new AvailableGamesList();
        //friendsList = new AvailableFriendsList();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // implement store tab here
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.fragment_store, container, false);

                rootView = populateGamesList(rootView);
                return rootView;
            }
            // implement community here
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView = inflater.inflate(R.layout.fragment_community, container, false);
                return rootView;

            // removed tab
            } else {
                View rootView = inflater.inflate(R.layout.fragment_library, container, false);
                return rootView;
            }

        }

        View populateGamesList(final View rootView) {
            ArrayList<String> gameNames;
            do {
                gameNames = MainActivity.gamesList.getGameNames();
            } while (gameNames.size() < 3);
            ListView list = (ListView) rootView.findViewById(R.id.list);
            list.setAdapter(new CustomListViewAdapter(getActivity(), R.layout.itemlistrow, MainActivity.gamesList));

            list.setClickable(true);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Game clickedGame =  (Game) parent.getItemAtPosition(position);
                    //Game clickedGame = MainActivity.gamesList.getGameByName(clickedGameName);

                    if (!clickedGame.isInstalled) {
                        String downloadConfirmation;
                        try {
                            if (clickedGame.download())
                                downloadConfirmation = "Download Complete";
                            else
                                downloadConfirmation = "Failed to Downlaod";
                        } catch (Exception e) {
                            e.printStackTrace();
                            downloadConfirmation = "IOException";
                        }

                        Toast.makeText(getActivity(), downloadConfirmation, Toast.LENGTH_SHORT).show();
                        populateGamesList(rootView);
                    }
                    else {
                        clickedGame.launch();
                    }
                }
            });
            return rootView;
        }
    }
    public static class CustomListViewAdapter extends ArrayAdapter<Game> {
        Context context;

        public CustomListViewAdapter(Context context, int resourceId) {
            super(context, resourceId);
        }

        public CustomListViewAdapter(Context context, int resource, AvailableGamesList gamesList) {
            super(context, resource, gamesList.getLinkedListOfGames());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.itemlistrow, null);
            }

            Game game = getItem(position);

            if (game != null) {
                TextView titleText = (TextView) v.findViewById(R.id.id);
                TextView downloadStatus = (TextView) v.findViewById(R.id.description);

                if (titleText != null) {
                    titleText.setText(game.getName());
                }

                if (downloadStatus != null) {
                    if (game.getIsInstalled()) {
                        downloadStatus.setText("Launch Game");
                    } else if (game.getIsDownloaded()) {
                        downloadStatus.setText("Install");
                    } else {
                        downloadStatus.setText("Download");
                    }
                }
            }
            return v;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Arcade";
                case 1:
                    return "Community";
            }
            return null;
        }
    }

    /**
     * Allows the store tab to send data to MainActivity
     */
    public class StoreInterface implements store.OnFragmentInteractionListener {

        @Override
        public void onFragmentInteraction(Uri uri) {
            // probably delete later
        }

    }

    /**
     * A list of games that may be retrieved from the games server.
     * For now, the games are just listed in the class. Update when server is implemented.
     */
    public class AvailableGamesList {
        private LinkedList<Game> listOfGames;

        AvailableGamesList() {
            this.listOfGames = new LinkedList<Game>();
            getFullGamesList();
        }

        public ArrayList<String> getGameNames() {
            ArrayList<String> gameNames = new ArrayList<String>();
            Iterator it = listOfGames.iterator();
            while (it.hasNext()) {
                Game game = (Game) it.next();
                gameNames.add(game.getName());
            }
            return gameNames;
        }

        public LinkedList<Game> getLinkedListOfGames() {
            return listOfGames;
        }

        public Game getGameByName(String name) {
            Iterator it = listOfGames.iterator();
            while (it.hasNext()) {
                Game game = (Game) it.next();
                if (game.getName() == name) {
                    return game;
                }
            }
            return null;
        }

        void getFullGamesList() {
            String request = "gamesList";
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new serverRequestGamesList().execute(request);
            } else {
                listOfGames.clear();
                Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
            }
        }

        void clear() {
            listOfGames.clear();
        }

        void checkForAPKs() {
            // iterate through listOfGames
            // check filesystem for APK
            // if APK exists, set pathToAPK and isDownloaded
        }

        void addGame(Game game) {
            listOfGames.add(game);
        }
    }


    /**
     * A game contains meta info and path to apk in android filesystem for each game as needed.
     */
    private class Game {
        private String name;
        private String pathToAPK;
        private boolean isDownloaded;
        private boolean isInstalled;


        Game(String name) {
            this.name = name;
            this.pathToAPK = null;
            this.isDownloaded = false;
            this.isInstalled = false;
            checkIfInstalled();
            if (isInstalled) {
                isDownloaded = true;
                isInstalled = true;
                pathToAPK = "storage/emulated/0/Arcade/Downloads/" + name + ".apk";
            }
        }

        String getName() {
            return this.name;
        }

        String getPathToAPK() {
            return pathToAPK;
        }

        void setPathToAPK(String pathToAPK) {
            this.pathToAPK = pathToAPK;
        }

//        boolean download() throws IOException {
//            // connect to server
//            String request = "download:" + name;
//            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//            if (networkInfo != null && networkInfo.isConnected()) {
//                new serverRequestGameDownload().execute(request);
//            } else {
//                Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
//            }
//
//            return false;
//        }

        boolean download() {
            InputStream inStream;
            OutputStream outStream;
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck == PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }

            File toDownloads = new File("storage/emulated/0/Arcade/Downloads/" + name + ".apk");
            if (toDownloads.exists()) {
                install();
                return true;
            }
            else {
                try {
                    File fromServer = new File("storage/emulated/0/Arcade/FromServer/" + name + ".apk");

                    File downloadDirectory = new File("storage/emulated/0/Arcade/Downloads/");
                    if (!downloadDirectory.exists()) {
                        downloadDirectory.mkdirs();
                    }

                    inStream = new FileInputStream(fromServer);
                    outStream = new FileOutputStream(toDownloads);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }

                    inStream.close();

                    outStream.flush();
                    outStream.close();


                    isDownloaded = true;
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        void launch() {
            Intent launch = getPackageManager().getLaunchIntentForPackage("com.dops." + name.toLowerCase());

            if (launch != null) {
                startActivity(launch);
            }
            else isInstalled = false;
        }

        void checkIfInstalled() {
            try {
                PackageManager pm = getPackageManager();
                pm.getPackageInfo("com.dops." + name.toLowerCase(), PackageManager.GET_ACTIVITIES);
                isInstalled = true;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                isInstalled = false;
            }
        }

        void setIsDownloaded() {
            isDownloaded = true;
        }

        void install() {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File("storage/emulated/0/Arcade/Downloads/" + name + ".apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            isInstalled = true;
            startActivity(intent);
        }

        boolean getIsDownloaded() {
            return isDownloaded;
        }

        boolean getIsInstalled() { return isInstalled; }
    }

    private class serverRequest extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... request) {
            byte[] gamesFromServer = new byte[3000000];
            try {
                Socket client = new Socket(host, 2121);

                sendRequest(client, request[0]);

                byte[] response = getResponse(client);

                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gamesFromServer;
        }

        boolean sendRequest(Socket socket, String request) {
            try {
                OutputStream toServer = socket.getOutputStream();
                PrintWriter messageToServer = new PrintWriter(toServer, true);
                messageToServer.println(request);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        byte[] getResponse(Socket client) {
            InputStream in;
            byte[] gamesFromServer = new byte[3000000];
            try {
                in = client.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                int nRead;
                while ((nRead = in.read(gamesFromServer, 0, gamesFromServer.length)) != -1) {
                    buffer.write(gamesFromServer, 0, nRead);
                }

                buffer.flush();
                client.close();

                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return gamesFromServer;
        }


    }

    private class serverRequestGamesList extends serverRequest {
        @Override
        protected void onPostExecute(byte[] result) {

            String gamesFromServer = new String(result);
            MainActivity.gamesList.clear();

            int i = 0;
            while (gamesFromServer != null) {
                int start = 0;
                int end = gamesFromServer.length();
                if (gamesFromServer.contains(",")) {
                    end = gamesFromServer.indexOf(",");
                }
                String gameName = gamesFromServer.substring(start, end);

                if (end < gamesFromServer.length())
                    gamesFromServer = gamesFromServer.substring(end + 1, gamesFromServer.length());
                else
                    gamesFromServer = null;

                MainActivity.gamesList.addGame(new Game(gameName));
                i++;
            }
        }
    }

    private class serverRequestGameDownload extends serverRequest {

        @Override
        protected byte[] doInBackground(String... request) {
            byte[] gamesFromServer = new byte[3000000];
            try {
                Socket client = new Socket(host, 2121);

                sendRequest(client, request[0]);

                gamesFromServer = getResponse(client);

                return gamesFromServer;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return gamesFromServer;
        }

        @Override
        protected void onPostExecute(byte[] result) {
            String fpGameToDownload = "Tetris.apk";
            File downloadFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fpGameToDownload);
            FileOutputStream stream ;

            try {
                if (downloadFile.exists()) {
                    downloadFile.delete();
                }
                stream = new FileOutputStream(downloadFile);

                //stream.write(result);
                stream.write(result);
                stream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Game game = MainActivity.gamesList.getGameByName("Tetris");
            game.setPathToAPK(downloadFile.getAbsolutePath());

            game.setIsDownloaded();
            game.install();
        }
    }
}

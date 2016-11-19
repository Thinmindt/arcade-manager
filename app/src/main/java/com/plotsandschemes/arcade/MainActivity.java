package com.plotsandschemes.arcade;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import layout.store;

public class MainActivity extends AppCompatActivity {

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
    public static final String host = "192.168.1.131";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gamesList = new AvailableGamesList();

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
        if (id == R.id.action_settings) {
            return true;
        }

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

                ArrayList<String> gameNames = null;
                do {
                    gameNames = MainActivity.gamesList.getGameNames();
                } while (gameNames == null);
                ListView list = (ListView) rootView.findViewById(R.id.list);
                list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, gameNames));

                list.setClickable(true);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String clickedGameName = ((TextView) view).getText().toString();
                        Game clickedGame = MainActivity.gamesList.getGameByName(clickedGameName);

                        String downloadConfirmation = "";
                        try {
                            if (clickedGame.download())
                                downloadConfirmation = "Download Complete";
                            else
                                downloadConfirmation = "Failed to Downlaod";
                        } catch (IOException e) {
                            e.printStackTrace();
                            downloadConfirmation = "IOException";
                        }

                        Toast.makeText(getActivity(), downloadConfirmation, Toast.LENGTH_SHORT).show();
                    }
                });
                return rootView;
            }
            // implement library here
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView = inflater.inflate(R.layout.fragment_library, container, false);
                return rootView;
                // implement community here
            } else {
                View rootView = inflater.inflate(R.layout.fragment_community, container, false);
                return rootView;
            }

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
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Store";
                case 1:
                    return "Library";
                case 2:
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

        public Game getGameByName(String name) {
            Iterator it = listOfGames.iterator();
            while (it.hasNext()) {
                Game game = (Game) it.next();
                if (name == game.getName())
                    return game;
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
        private Path pathToAPK;
        private boolean isDownloaded;

        Game(String name, Path pathToAPK) {
            this.name = name;
            this.pathToAPK = pathToAPK;
            this.isDownloaded = true;
        }

        Game(String name) {
            this.name = name;
            this.pathToAPK = null;
            this.isDownloaded = false;
        }

        String getName() {
            return this.name;
        }

        Path getPathToAPK() {
            return pathToAPK;
        }

        void setPathToAPK(Path pathToAPK) {
            this.pathToAPK = pathToAPK;
        }

        boolean download() throws IOException {
            // connect to server
            String request = "download:" + name;
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new serverRequestGameDownload().execute(request);
            } else {
                Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_SHORT).show();
            }

            return false;
        }

        boolean checkIfDownloaded() {
            return isDownloaded;
        }
    }

    private class serverRequest extends AsyncTask<String, Void, byte[]> {

        @Override
        protected byte[] doInBackground(String... request) {
            byte[] gamesFromServer = new byte[1024];
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

        private byte[] getResponse(Socket client) {
            InputStream in = null;
            byte[] gamesFromServer = new byte[1024];
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
        protected void onPostExecute(byte[] result) {

            String fpGameToDownload = "Tetris";
            File downloadFile = new File(getFilesDir(), fpGameToDownload);
            try {
                FileOutputStream stream = new FileOutputStream(downloadFile);
                stream.write(result);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // set PathToAPK (or set something so that the library tab knows where to find it)
            // Game game = new Game();
            // this.isDownloaded = true;
        }
    }
}
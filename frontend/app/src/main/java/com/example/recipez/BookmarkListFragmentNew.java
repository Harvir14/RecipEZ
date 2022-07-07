package com.example.recipez;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkListFragmentNew extends Fragment {
    final static String TAG = "BookmarkListFragmentNew";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView bookmarkListRecyclerView;
    private Button addNewFolderButton;

    public BookmarkListFragmentNew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookmarkListFragmentNew newInstance(String param1, String param2) {
        BookmarkListFragmentNew fragment = new BookmarkListFragmentNew();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark_list_new, container, false);
    }

    String dummyList = "[\n" +
            "    {\n" +
            "        \"id\": 715538,\n" +
            "        \"title\": \"What to make for dinner tonight?? Bruschetta Style Pork & Pasta\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/715538-312x231.jpg\",\n" +
            "        \"path\": \"folder1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 631807,\n" +
            "        \"title\": \"Toasted\\\" Agnolotti (or Ravioli)\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/631807-312x231.jpg\",\n" +
            "        \"path\": \"folder1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 655589,\n" +
            "        \"title\": \"Penne with Goat Cheese and Basil\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/655589-312x231.jpg\",\n" +
            "        \"path\": \"folder1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 631870,\n" +
            "        \"title\": \"4th of July RASPBERRY, WHITE & BLUEBERRY FARM TO TABLE Cocktail From Harvest Spirits\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/631870-312x231.jpg\",\n" +
            "        \"path\": \"folder2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 647465,\n" +
            "        \"title\": \"Hot Garlic and Oil Pasta\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/647465-312x231.jpg\",\n" +
            "        \"path\": \"folder2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 660101,\n" +
            "        \"title\": \"Simple Garlic Pasta\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/660101-312x231.jpg\",\n" +
            "        \"path\": \"folder2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 650132,\n" +
            "        \"title\": \"Linguine With Chick Peas and Bacon\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/650132-312x231.jpg\",\n" +
            "        \"path\": \"folder3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 654830,\n" +
            "        \"title\": \"Pasta Con Pepe E Caciotta Al Tartufo\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/654830-312x231.jpg\",\n" +
            "        \"path\": \"folder3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 633876,\n" +
            "        \"title\": \"Baked Ziti\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/633876-312x231.jpg\",\n" +
            "        \"path\": \"folder3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 634995,\n" +
            "        \"title\": \"Bird's Nest Marinara\",\n" +
            "        \"image\": \"https://spoonacular.com/recipeImages/634995-312x231.jpg\",\n" +
            "        \"path\": \"\"\n" +
            "    }\n" +
            "]";
    private List<BookmarkFolder> folderList;
    private BookmarkFolderAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookmarkListRecyclerView = view.findViewById(R.id.bookmark_list_recycler_view);
        // bookmarkListRecyclerView.setHasFixedSize(true);
        bookmarkListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addNewFolderButton = view.findViewById(R.id.add_folder_dialog_button);
        addNewFolderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_new_folder);

                EditText folderNameInput = dialog.findViewById(R.id.dialog_new_folder_name_input);
                Button confirmButton = dialog.findViewById(R.id.dialog_new_folder_confirm_button);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String folderName = "";

                        if (!folderNameInput.getText().toString().equals("")) {
                            folderName = folderNameInput.getText().toString();
                        } else {
                            Toast.makeText(getActivity(), "Please enter a folder name", Toast.LENGTH_SHORT).show();
                        }

                        List<JSONObject> emptyRecipeList = new ArrayList<>();
                        folderList.add(new BookmarkFolder(emptyRecipeList, folderName));
                        addPathToPathsList(1, folderName); // TODO: update user ID

                        adapter.notifyItemInserted(folderList.size() - 1);
                        bookmarkListRecyclerView.scrollToPosition(folderList.size() - 1);
                    }
                });

                dialog.show();
            }
        });

        List<String> folderNames = new ArrayList<>(); // from backend
        folderNames.add("folder1");
        folderNames.add("folder2");
        folderNames.add("folder3");

        folderList = new ArrayList<>();

        List<JSONObject> uncategorizedRecipes = new ArrayList<>();

        try {
            JSONArray recipesArray = new JSONArray(dummyList);
            JSONArray recipesArrayCopy = recipesArray;

            for (int i = 0; i < folderNames.size(); i++) {
                List<JSONObject> recipesInThisFolder = new ArrayList<>();

                for (int j = 0; j < recipesArrayCopy.length(); j++) {
                    JSONObject recipeObject = recipesArrayCopy.getJSONObject(j);
                    String recipeFolderPath = recipeObject.getString("path");

                    if (recipeFolderPath.equals(folderNames.get(i))) {
                        recipesInThisFolder.add(recipeObject);
                    } else if (recipeFolderPath.equals("")) {
                        uncategorizedRecipes.add(recipeObject);
                        recipesArrayCopy.remove(j);
                    }
                }

                BookmarkFolder thisFolder = new BookmarkFolder(recipesInThisFolder, folderNames.get(i));
                folderList.add(thisFolder);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BookmarkFolder folderUncategorized = new BookmarkFolder(uncategorizedRecipes, "uncategorized");
        folderList.add(folderUncategorized);

        adapter = new BookmarkFolderAdapter(folderList);
        bookmarkListRecyclerView.setAdapter(adapter);
    }

    public String encodeString(String s) {
        String result;
        try {
            result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } // This exception should never occur.
        catch (Exception e) {
            result = s;
        }

        return result;
    }

    public String convertArrayToString(String[] s) {
        String result = "";

        for (int i = 0; i < s.length; i++) {
            if (i == s.length - 1) {
                result+= s[i];
            }
            else {
                result+= s[i] + ",";
            }
        }
        return result;
    }

    public String convertJSONArrayToString(JSONArray s) throws JSONException {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            if (i == s.length() - 1) {
                result+= s.get(i);
            }
            else {
                result+= s.get(i) + ",";
            }
        }
        return result;
    }

    public final class Recipe extends MainActivity {
        private int myStaticMember;
        private String TAG = "Recipe Class";

        public Recipe () {
            myStaticMember = 1;
        }

        public void addRecipeToBookmarkList(int userID, int recipeID, String path, String title, String image) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://20.53.224.7:8084/addRecipe";
            // 10.0.2.2 is a special alias to localhost for developers

            Map<String, String> jsonParams = new HashMap();
            jsonParams.put("userID", String.valueOf(userID));
            jsonParams.put("recipeID", String.valueOf(recipeID));
            jsonParams.put("path", path);
            jsonParams.put("title", title);
            jsonParams.put("image", image);

            // Request a string response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.POST, url, new JSONObject(jsonParams),new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    }) {
            };

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        }

        public void removeRecipeFromBookmarkList(int userID, int recipeID) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://20.53.224.7:8084/removeRecipe";
            // 10.0.2.2 is a special alias to localhost for developers

            Map<String, String> jsonParams = new HashMap();
            jsonParams.put("userID", String.valueOf(userID));
            jsonParams.put("recipeID", String.valueOf(recipeID));

            // Request a string response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.DELETE, url, new JSONObject(jsonParams),new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    }) {
            };

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        }

        public void getRecipesFromBookmarkList(int userID) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://20.53.224.7:8084/getRecipes?userid=" + userID;
            // 10.0.2.2 is a special alias to localhost for developers

            // Request a string response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);

        }

        public void filterRecipes(String[] ingredients, String[] filters, String[] restrictions) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String ingredientsList = encodeString(convertArrayToString(ingredients));
            String filtersList = encodeString(convertArrayToString(filters));
            String restrictionsList = encodeString(convertArrayToString(restrictions));

            String url = "http://20.53.224.7:8084/requestFilteredRecipes?ingredients=" + ingredientsList + "&restrictions=" + restrictionsList + "&filters=" + filtersList;
            // 10.0.2.2 is a special alias to localhost for developers

            // Request a string response from the provided URL.
            JsonArrayRequest jsonRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        }

        public void getSuggestedRecipes(String[] ingredients, String[] restrictions) {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String ingredientsList = encodeString(convertArrayToString(ingredients));
            String restrictionsList = encodeString(convertArrayToString(restrictions));

            String url = "http://20.53.224.7:8084/generateSuggestedRecipesList?ingredientsinpantry=" + ingredientsList + "&restrictions=" + restrictionsList;
            // 10.0.2.2 is a special alias to localhost for developers

            // Request a string response from the provided URL.
            JsonArrayRequest jsonRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);

        }

        public void searchRecipe(String recipeName) {// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://20.53.224.7:8084/searchRecipe?recipename=" + recipeName;
            // 10.0.2.2 is a special alias to localhost for developers

            // Request a string response from the provided URL.
            JsonArrayRequest jsonRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);

        }


    }

    public void addPathToPathsList(int userID, String path) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://10.0.2.2:8084/addNewPath";
        // 10.0.2.2 is a special alias to localhost for developers

        Map<String, String> jsonParams = new HashMap();
        jsonParams.put("userID", String.valueOf(userID));
        jsonParams.put("path", path);


        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(jsonParams),new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }) {
        };

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }

    public void removePathFromPathsList(int userID, String path) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://10.0.2.2:8084/removeExistingPath";
        // 10.0.2.2 is a special alias to localhost for developers

        Map<String, String> jsonParams = new HashMap();
        jsonParams.put("userID", String.valueOf(userID));
        jsonParams.put("path", path);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.DELETE, url, new JSONObject(jsonParams),new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }) {
        };

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public void getPathsList(int userID) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "http://10.0.2.2:8084/getAllPaths?userid=" + userID;
        // 10.0.2.2 is a special alias to localhost for developers

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }
}


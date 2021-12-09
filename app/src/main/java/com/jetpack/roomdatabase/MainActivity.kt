package com.jetpack.roomdatabase

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jetpack.roomdatabase.entity.SampleEntity
import com.jetpack.roomdatabase.viewmodel.SampleViewModel
import com.jetpack.roomdatabase.viewmodel.SampleViewModelFactory
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private val myViewmodel by viewModels<MyViewmodel2>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            val sampleViewModel: SampleViewModel = viewModel(
                factory = SampleViewModelFactory(context.applicationContext as Application)
            )
            Scaffold(scaffoldState = scaffoldState) {
                CallDatabase(myViewmodel = myViewmodel, scope, scaffoldState)
            }
        }
    }
}

val date = SimpleDateFormat("dd-MM-yyyy")
val strDate: String = date.format(Date())

data class User(
    val uid: Int?,
    val uname: String,
    val udesc: String,
    val uimgUrl: String,
    val ucreateDate: String
)
val user = User(null, "", "", "","")
//val user = SampleEntity(id = null, "","","","")

@Composable
fun CallDatabase(myViewmodel: MyViewmodel2, scope: CoroutineScope, scaffoldState: ScaffoldState) {

    val context = LocalContext.current
    val sampleViewModel: SampleViewModel = viewModel(
        factory = SampleViewModelFactory(context.applicationContext as Application)
    )
    val insertSampleDataY = listOf(
        SampleEntity(name="${myViewmodel.text}", desc="${myViewmodel.description}", imgUrl="Empty Image Url", createdDate=strDate),
    )
    val users2 = remember {
        mutableStateListOf(user)
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column {


        //if (users2?.first().uname.isBlank() ) {users2.clear()}
        MyTextField(
            label = "User Name",
            value = myViewmodel.text,
            onValueChanged = { myViewmodel.onTextChanged(it) }
        )
        Spacer(modifier = Modifier.height(2.dp))
        MyTextField(
            label = "Description",
            value = myViewmodel.description,
            onValueChanged = { myViewmodel.onPasswordChanged(it) }
        )
        }
        Spacer(modifier = Modifier.height(2.dp))
        UserList(listOfOsers = users2, sampleViewModel)

        Button(
            onClick = {
                /*scope.launch {
                    scaffoldState
                        .snackbarHostState
                        .showSnackbar("Hello, ${myViewmodel.text}")
                }*/
                if (myViewmodel.text.isNotBlank())  {
                    val lastItem =  users2.last().uid
                    users2.add(User(uid = if (lastItem != null) lastItem +1 else 1, uname = "${myViewmodel.text}", "${myViewmodel.description}", uimgUrl = "${myViewmodel.imgUrl}", ucreateDate = "${myViewmodel.date}"))
                }
                sampleViewModel.addSample(insertSampleDataY)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(112.dp)
                .height(30.dp),

            enabled = myViewmodel.text.isNotBlank() /*&& myViewmodel.password.isNotBlank()*/,
        ) {
            Text(text = "Submit")
        }
        Spacer(modifier = Modifier.height(8.dp))
        /*Button(
            onClick = {
            sampleViewModel.addSample(insertSampleDataX)
            users2.add(User(1, "kk"))
                         },
            modifier = Modifier
            .padding(25.dp)
            .align(Alignment.BottomCenter)
        ) {
            Text(text = "Add one" )
        }*/
        Button(
            onClick = {
                sampleViewModel.deleteAllRecord()
                users2.clear()
            },
            modifier = Modifier
                //.padding(25.dp)
                .align(Alignment.TopEnd)
                .padding(vertical = 32.dp)
                .width(112.dp)
                .height(30.dp)
        ) {
            Text(text = "Delete All" )
        }
        /*Button(
            onClick = {
                //sampleViewModel.deleteAllRecord()
                users2.removeLast()
            },
            modifier = Modifier
                .padding(25.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Delete one" )
        }
        Button(
            onClick = {

                },
            modifier = Modifier
                .padding(25.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(text = "Show in log" )
        }*/
    }
}



@Composable
fun UserList(listOfOsers: MutableList<User>, sampleViewModel: SampleViewModel){

    LazyColumn (modifier = Modifier
        .fillMaxSize()
        .padding(top = 120.dp)){
        items(listOfOsers){ user ->
            if (user.uname.isNotEmpty()) {UserCard(CardUser = user, myViewmodel = MyViewmodel2(), listOfOsers, sampleViewModel)}
        }
    }
}

@Composable
fun UserCard(
    CardUser: User,
    myViewmodel: MyViewmodel2,
    listOfOsers: MutableList<User>,
    sampleViewModel: SampleViewModel
)
{
    val insertSampleDataTest = listOf(
        SampleEntity(name="testUser", desc="testDesc", imgUrl="Empty Image Url", createdDate=strDate),
    )
    /*val users2 = remember {
        mutableStateListOf(user)
    }*/
    Card (
        elevation = 14.dp,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .padding(12.dp)
            /*.border(width = 1.dp, color = Gray)   */ ) {
            Image(
                painter = painterResource(id = R.drawable.confluence_),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text =  "${CardUser.uid}")
                Text(text =  "${CardUser.uname}")
                Text(text =  "${CardUser.udesc}")
                Button(onClick = {
                    listOfOsers.remove(CardUser)
                    sampleViewModel.deleteSample(
                        item = SampleEntity(
                            CardUser.uid,
                            CardUser.uname,
                            CardUser.udesc,
                            CardUser.uimgUrl,
                            CardUser.ucreateDate,
                        )
                    )  /// GO ON HERE
                } ) {
                    Text(text = "Delete")

                }
            }
        }
    }
}




@Composable
fun MyTextField(
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    value: String,
    onValueChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}


class MyViewmodel2 : ViewModel() {
    //state
    var text by mutableStateOf("")
    var description by mutableStateOf("")
    var imgUrl by mutableStateOf("")
    var date by mutableStateOf("")
    // events
    fun onTextChanged(newString: String) {
        text = newString
    }
    fun onPasswordChanged(newString: String) {
        description = newString
    }
    fun onimgUrlChanged(newString: String) {
        imgUrl = newString
    }
    fun onDateChanged(newString: String) {
        date = newString
    }
}


/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val viewModel = MyViewmodel2()
        CallDatabase(myViewmodel = viewModel, scope = scope,
            scaffoldState = scaffoldState)

@Composable
fun MainContent(){
    Box(modifier = Modifier.fillMaxSize()){
        val user = User(1)

        val users2 = remember {
            mutableStateListOf(user)
        }
        UserList(users = users2)
        Button(onClick = { users2.add(User(1)) }, modifier = Modifier
            .padding(25.dp)
            .align(Alignment.BottomCenter)
        ) {
            Text(text = "Add one" )
        }
    }

}*/
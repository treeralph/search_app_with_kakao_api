package com.example.camp_challenge_kakao_api.data.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.camp_challenge_kakao_api.SHARED_PREFERENCES
import com.example.camp_challenge_kakao_api.URGENT_TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class BookmarkLocalDataSource(
    private val context: Context,
    private val key: String,
    private val defValue: String,
) {


    private val preferences = context.getSharedPreferences(SHARED_PREFERENCES, 0)

    private val _element = MutableStateFlow(defValue)
    val element = _element.asStateFlow()

    init {
        _element.value = preferences.getString(key, defValue) ?: defValue
        setPreferencesChangeListener()
    }

    fun edit(target: String) {
        Log.e(URGENT_TAG, "edit: called", )
        preferences.edit().putString(key, target).apply()
    }


    // edit 안되는 문제는 change listener 문제 인듯하다.
    // 먼저, 북마크를 추가했을때 리스터가 동작하지 않는다.
    // 하지만 어플리케이션을 재시작하면 북마크에 추가된 것을 확인 할 수 있다.
    // 리스너가 GC의 대상이 되었다? 지 맘대로?

    /**
     *
     * WeakHashMap 찾아보기 -> 매우 신기하네 참조가 사라지면 삭제해 버림
     * 그러니까 아래의 함수 setPreferencesChangeListener가 호출되고 temp reference가 만들어지잖아.
     * 그리고 해당 함수의 호출이 종료되면 reference는 GC의 대상이 되겠지?
     * GC의 대상이 된 참조를 HashMap에서 삭제해 버리는 작업을 진행하게 되는 거야.
     *
     * 따라서, reference를 계속 살려 놔야해
     * 이 클래스를 Object로 만들고 object class의 프로퍼티로서 리스너를 생성한 다음
     * 달아주면 계속 살아있지 않을까?
     *
     * */

    private fun setPreferencesChangeListener() {

        // new one
        val temp = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            Log.e(URGENT_TAG, "setPreferencesChangeListener: called", )
            _element.value = sharedPreferences.getString(key, defValue) ?: defValue
        }
        preferences.registerOnSharedPreferenceChangeListener(temp)
        //

//        preferences.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
//            Log.e(URGENT_TAG, "setPreferencesChangeListener: called", )
//            _element.value = sharedPreferences.getString(key, defValue) ?: defValue
//        }
    }
}
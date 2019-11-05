package xin.spring.library.listener;

import xin.spring.library.dao.LibraryDBDao;

/**
 * fragment 与 activity 交互 接口
 */
public interface OnFragmentInteractionListener {

    LibraryDBDao getDao();

}

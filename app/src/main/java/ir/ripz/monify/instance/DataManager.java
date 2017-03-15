package ir.ripz.monify.instance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ir.ripz.monify.model.DateModel;
import ir.ripz.monify.model.DutyModel;
import ir.ripz.monify.model.InterestModel;
import ir.ripz.monify.util.SolarCalendar;

public class DataManager {
    private SharedPreferences pref;
    private final static String PREF_ITEM = "PREF_ITEM";

    public final static float DEFAULT = 1000;

    public DataManager(Context context) {
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void addItem(DutyModel model) {
        ArrayList<DutyModel> list = getItems();
        list.add(model);
        pref.edit().putString(PREF_ITEM, new Gson().toJson(list)).apply();
    }

    private ArrayList<DutyModel> getItems() {
        ArrayList<DutyModel> list = new ArrayList<>();
        DutyModel models[] = new Gson().fromJson(pref.getString(PREF_ITEM, null), DutyModel[].class);
        if (models != null) {
            list.addAll(Arrays.asList(models));
        }
        return list;
    }

    public long getTodayAll() {
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        DateModel date = new SolarCalendar().get();
        for (DutyModel model : list) {
            if (model.getDateModel().getMonth() == date.getMonth()
                    && model.getDateModel().getYear() == date.getYear()
                    && model.getDateModel().getDay() == date.getDay()) {
                sum = sum + model.getDuty();
            }
        }
        return sum;
    }

    public float getTodayInterestPercent(InterestModel interest) {
        float percent = 0;
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        DateModel date = new SolarCalendar().get();
        for (DutyModel data : list) {
            DateModel model = data.getDateModel();
            if (model.getDay() == date.getDay()
                    && model.getMonth() == date.getMonth()
                    && model.getYear() == date.getYear()
                    && data.getInterest().getId() == interest.getId()) {
                sum = sum + data.getDuty();
            }
        }
        long all = getTodayAll();
        if (all == 0) {
            return DEFAULT;
        }
        percent = 1f * sum / all * 100;
        return percent;
    }

    public long getTodayInterest(InterestModel interest) {
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        DateModel date = new SolarCalendar().get();
        for (DutyModel data : list) {
            DateModel model = data.getDateModel();
            if (model.getMonth() == date.getMonth()
                    && model.getYear() == date.getYear()
                    && model.getDay() == date.getDay()
                    && data.getInterest().getId() == interest.getId()) {
                sum = sum + data.getDuty();
            }
        }
        return sum;
    }

    public ArrayList<DateModel> getRange() {
        ArrayList<DateModel> copy = new ArrayList<>();
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            copy.add(model.getDateModel());
        }
        return copy;
    }

    public void delete(DutyModel dataModel) {
        ArrayList<DutyModel> copy = new ArrayList<>();
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            if (!model.getId().equals(dataModel.getId())) {
                copy.add(model);
            }
        }
        pref.edit().putString(PREF_ITEM, new Gson().toJson(copy)).apply();
    }

    public void edit(DutyModel dataModel) {
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            if (model.getId().equals(dataModel.getId())) {
                model.setTitle(dataModel.getTitle());
                model.setDuty(dataModel.getDuty());
                model.setInterest(dataModel.getInterest());
                break;
            }
        }
        pref.edit().putString(PREF_ITEM, new Gson().toJson(list)).apply();
    }

    public ArrayList<DutyModel> getSingleDayItems(DateModel date) {
        ArrayList<DutyModel> copy = new ArrayList<>();
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            if (model.getDateModel().getDay() == date.getDay()
                    && model.getDateModel().getMonth() == date.getMonth()
                    && model.getDateModel().getYear() == date.getYear()) {
                copy.add(model);
            }
        }
        Collections.sort(copy, new Comparator<DutyModel>() {
            @Override
            public int compare(DutyModel lhs, DutyModel rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });
        return copy;
    }

    public long getSingleDay(DateModel date) {
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            if (model.getDateModel().getDay() == date.getDay()
                    && model.getDateModel().getMonth() == date.getMonth()
                    && model.getDateModel().getYear() == date.getYear()) {
                sum = sum + model.getDuty();
            }
        }
        return sum;
    }

    public String test() {
        return String.valueOf(getItems().size());
    }


    public ArrayList<DutyModel> getRangeItems(DateModel min, DateModel max) {
        ArrayList<DutyModel> copy = new ArrayList<>();
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            DateModel date = model.getDateModel();
            if (date.getYear() > min.getYear() && date.getYear() < max.getYear()) {
                copy.add(model);
            } else if (date.getYear() == min.getYear() && date.getYear() == max.getYear()) {
                if (date.getMonth() > min.getMonth() && date.getMonth() < max.getMonth()) {
                    copy.add(model);
                } else if (date.getMonth() == min.getMonth() && date.getMonth() == max.getMonth()) {
                    if (date.getDay() >= min.getDay() && date.getDay() <= max.getDay()) {
                        copy.add(model);
                    }
                } else if (date.getMonth() == min.getMonth() && date.getMonth() < max.getMonth()) {
                    if (date.getDay() >= min.getDay()) {
                        copy.add(model);
                    }
                } else if (date.getMonth() == max.getMonth() && date.getMonth() > min.getMonth()) {
                    if (date.getDay() <= max.getDay()) {
                        copy.add(model);
                    }
                }
            } else if (date.getYear() == min.getYear() && date.getYear() < max.getYear()) {
                if (date.getMonth() > min.getMonth()) {
                    copy.add(model);
                } else if (date.getMonth() == min.getMonth()) {
                    if (date.getDay() >= min.getDay()) {
                        copy.add(model);
                    }
                }
            } else if (date.getYear() == max.getYear() && date.getYear() > min.getYear()) {
                if (date.getMonth() < max.getMonth()) {
                    copy.add(model);
                } else if (date.getMonth() == max.getMonth()) {
                    if (date.getDay() <= max.getDay()) {
                        copy.add(model);
                    }
                }
            }
        }
        Collections.sort(copy, new Comparator<DutyModel>() {
            @Override
            public int compare(DutyModel lhs, DutyModel rhs) {
                return rhs.getDate().compareTo(lhs.getDate());
            }
        });
        return copy;
    }

    public float getRangeInterestPercent(InterestModel interest, DateModel min, DateModel max) {
        float percent = 0;
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            if (model.getInterest().getId() == interest.getId()) {
                DateModel date = model.getDateModel();
                if (date.getYear() > min.getYear() && date.getYear() < max.getYear()) {
                    sum = sum + model.getDuty();
                } else if (date.getYear() == min.getYear() && date.getYear() == max.getYear()) {
                    if (date.getMonth() > min.getMonth() && date.getMonth() < max.getMonth()) {
                        sum = sum + model.getDuty();
                    } else if (date.getMonth() == min.getMonth() && date.getMonth() == max.getMonth()) {
                        if (date.getDay() >= min.getDay() && date.getDay() <= max.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    } else if (date.getMonth() == min.getMonth() && date.getMonth() < max.getMonth()) {
                        if (date.getDay() >= min.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    } else if (date.getMonth() == max.getMonth() && date.getMonth() > min.getMonth()) {
                        if (date.getDay() <= max.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    }
                } else if (date.getYear() == min.getYear() && date.getYear() < max.getYear()) {
                    if (date.getMonth() > min.getMonth()) {
                        sum = sum + model.getDuty();
                    } else if (date.getMonth() == min.getMonth()) {
                        if (date.getDay() >= min.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    }
                } else if (date.getYear() == max.getYear() && date.getYear() > min.getYear()) {
                    if (date.getMonth() < max.getMonth()) {
                        sum = sum + model.getDuty();
                    } else if (date.getMonth() == max.getMonth()) {
                        if (date.getDay() <= max.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    }
                }
            }
        }
        long all = getRangeAll(min, max);
        if (all == 0) {
            return DEFAULT;
        }
        percent = 1f * sum / all * 100;
        return percent;
    }


    public long getRangeAll(DateModel min, DateModel max) {
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            DateModel date = model.getDateModel();
            if (date.getYear() > min.getYear() && date.getYear() < max.getYear()) {
                sum = sum + model.getDuty();
            } else if (date.getYear() == min.getYear() && date.getYear() == max.getYear()) {
                if (date.getMonth() > min.getMonth() && date.getMonth() < max.getMonth()) {
                    sum = sum + model.getDuty();
                } else if (date.getMonth() == min.getMonth() && date.getMonth() == max.getMonth()) {
                    if (date.getDay() >= min.getDay() && date.getDay() <= max.getDay()) {
                        sum = sum + model.getDuty();
                    }
                } else if (date.getMonth() == min.getMonth() && date.getMonth() < max.getMonth()) {
                    if (date.getDay() >= min.getDay()) {
                        sum = sum + model.getDuty();
                    }
                } else if (date.getMonth() == max.getMonth() && date.getMonth() > min.getMonth()) {
                    if (date.getDay() <= max.getDay()) {
                        sum = sum + model.getDuty();
                    }
                }
            } else if (date.getYear() == min.getYear() && date.getYear() < max.getYear()) {
                if (date.getMonth() > min.getMonth()) {
                    sum = sum + model.getDuty();
                } else if (date.getMonth() == min.getMonth()) {
                    if (date.getDay() >= min.getDay()) {
                        sum = sum + model.getDuty();
                    }
                }
            } else if (date.getYear() == max.getYear() && date.getYear() > min.getYear()) {
                if (date.getMonth() < max.getMonth()) {
                    sum = sum + model.getDuty();
                } else if (date.getMonth() == max.getMonth()) {
                    if (date.getDay() <= max.getDay()) {
                        sum = sum + model.getDuty();
                    }
                }
            }
        }
        return sum;
    }

    public long getRangeInterest(InterestModel interest, DateModel min, DateModel max) {
        long sum = 0;
        ArrayList<DutyModel> list = getItems();
        for (DutyModel model : list) {
            if (model.getInterest().getId() == interest.getId()) {
                DateModel date = model.getDateModel();
                if (date.getYear() > min.getYear() && date.getYear() < max.getYear()) {
                    sum = sum + model.getDuty();
                } else if (date.getYear() == min.getYear() && date.getYear() == max.getYear()) {
                    if (date.getMonth() > min.getMonth() && date.getMonth() < max.getMonth()) {
                        sum = sum + model.getDuty();
                    } else if (date.getMonth() == min.getMonth() && date.getMonth() == max.getMonth()) {
                        if (date.getDay() >= min.getDay() && date.getDay() <= max.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    } else if (date.getMonth() == min.getMonth() && date.getMonth() < max.getMonth()) {
                        if (date.getDay() >= min.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    } else if (date.getMonth() == max.getMonth() && date.getMonth() > min.getMonth()) {
                        if (date.getDay() <= max.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    }
                } else if (date.getYear() == min.getYear() && date.getYear() < max.getYear()) {
                    if (date.getMonth() > min.getMonth()) {
                        sum = sum + model.getDuty();
                    } else if (date.getMonth() == min.getMonth()) {
                        if (date.getDay() >= min.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    }
                } else if (date.getYear() == max.getYear() && date.getYear() > min.getYear()) {
                    if (date.getMonth() < max.getMonth()) {
                        sum = sum + model.getDuty();
                    } else if (date.getMonth() == max.getMonth()) {
                        if (date.getDay() <= max.getDay()) {
                            sum = sum + model.getDuty();
                        }
                    }
                }
            }
        }
        return sum;
    }
}
